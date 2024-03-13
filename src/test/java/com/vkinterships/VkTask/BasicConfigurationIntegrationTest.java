package com.vkinterships.VkTask;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class BasicConfigurationIntegrationTest {

    TestRestTemplate restTemplate;
    URL base;

    @LocalServerPort
    int port;

    @Test
    public void whenLoggedPostRequests_ThenSuccess()
            throws IllegalStateException, IOException {

        restTemplate = new TestRestTemplate("post", "password");

        getResponseAndCheck("http://localhost:" + port + "/api/posts/1",
                HttpStatus.OK,
                true,
                "{\n" +
                        "  \"userId\": 1,\n" +
                        "  \"id\": 1,\n" +
                        "  \"title\": \"sunt aut facere repellat provident occaecati " +
                        "excepturi optio reprehenderit\",\n" +
                        "  \"body\": \"quia et suscipit\\nsuscipit recusandae consequuntur expedita et " +
                        "cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem " +
                        "eveniet architecto\"\n" +
                        "}");
    }

    @Test
    public void whenUserWithWrongCredentials_thenUnauthorizedPage()
            throws Exception {

        restTemplate = new TestRestTemplate("user", "wrongpassword");
        base = new URL("http://localhost:" + port + "/api/posts/1");

        getResponseAndCheck("http://localhost:" + port + "/api/posts/1",
                HttpStatus.UNAUTHORIZED,
                false, "");
    }

    @Test
    public void whenLoggedUserRequests_ThenSuccess()
            throws IllegalStateException, IOException {

        restTemplate = new TestRestTemplate("user", "password");

        getResponseAndCheck("http://localhost:" + port + "/api/users/1?name=Leanne Graham",
                HttpStatus.OK,
                true,
                "{\n" +
                        "  \"id\": 1,\n" +
                        "  \"name\": \"Leanne Graham\",\n" +
                        "  \"username\": \"Bret\",\n" +
                        "  \"email\": \"Sincere@april.biz\",\n" +
                        "  \"address\": {\n" +
                        "    \"street\": \"Kulas Light\",\n" +
                        "    \"suite\": \"Apt. 556\",\n" +
                        "    \"city\": \"Gwenborough\",\n" +
                        "    \"zipcode\": \"92998-3874\",\n" +
                        "    \"geo\": {\n" +
                        "      \"lat\": \"-37.3159\",\n" +
                        "      \"lng\": \"81.1496\"\n" +
                        "    }\n" +
                        "  },\n" +
                        "  \"phone\": \"1-770-736-8031 x56442\",\n" +
                        "  \"website\": \"hildegard.org\",\n" +
                        "  \"company\": {\n" +
                        "    \"name\": \"Romaguera-Crona\",\n" +
                        "    \"catchPhrase\": \"Multi-layered client-server neural-net\",\n" +
                        "    \"bs\": \"harness real-time e-markets\"\n" +
                        "  }\n" +
                        "}");
    }

    @Test
    void userDoRequestToAlbums() throws MalformedURLException {
        restTemplate = new TestRestTemplate("user", "password");

        getResponseAndCheck("http://localhost:" + port + "/api/albums",
                HttpStatus.FORBIDDEN,
                false,
                "");
    }

    @Test
    void tryHttpParams() throws MalformedURLException {
        restTemplate = new TestRestTemplate("albums", "password");

        getResponseAndCheck("http://localhost:" + port + "/api/albums?id=1&userId=1",
                HttpStatus.OK,
                true,
                "[\n" +
                        "  {\n" +
                        "    \"userId\": 1,\n" +
                        "    \"id\": 1,\n" +
                        "    \"title\": \"quidem molestiae enim\"\n" +
                        "  }\n" +
                        "]");
    }

    @Test
    void checkPUT() {
        restTemplate = new TestRestTemplate("post", "password");


        String url = "http://localhost:" + port + "/api/posts/1";
        HttpEntity<String> request =
                new HttpEntity<>("{\"id\":1,\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}");
        ResponseEntity<String> response = restTemplate
                .exchange(url, HttpMethod.PUT, request, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\n" +
                "  \"id\": 1,\n" +
                "  \"title\": \"foo\",\n" +
                "  \"body\": \"bar\",\n" +
                "  \"userId\": 1\n" +
                "}", response.getBody());

    }

    private void getResponseAndCheck(String url, HttpStatus expected, boolean checkBody, String body) throws MalformedURLException {
        ResponseEntity<String> response =
                restTemplate.getForEntity(new URL(url).toString(), String.class);

        assertEquals(expected, response.getStatusCode());

        if (checkBody) {
            assertEquals(body, response.getBody());
        }
    }
}