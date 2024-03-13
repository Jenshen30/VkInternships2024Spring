package com.vkinterships.VkTask;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
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
    public void whenLoggedPostRequestsHomePage_ThenSuccess()
            throws IllegalStateException, IOException {

        restTemplate = new TestRestTemplate("post", "password");
        base = new URL("http://localhost:" + port);

        ResponseEntity<String> response =
                restTemplate.getForEntity(new URL("http://localhost:" + port + "/api/posts/1").toString(), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), "{\n" +
                "  \"userId\": 1,\n" +
                "  \"id\": 1,\n" +
                "  \"title\": \"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\n" +
                "  \"body\": \"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto\"\n" +
                "}");
    }

    @Test
    public void whenUserWithWrongCredentials_thenUnauthorizedPage()
            throws Exception {

        restTemplate = new TestRestTemplate("user", "wrongpassword");
        base = new URL("http://localhost:" + port + "/api/posts/1");

        ResponseEntity<String> response =
                restTemplate.getForEntity(base.toString(), String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void whenLoggedUserRequestsHomePage_ThenSuccess()
            throws IllegalStateException, IOException {

        restTemplate = new TestRestTemplate("user", "password");
        base = new URL("http://localhost:" + port);

        ResponseEntity<String> response =
                restTemplate.getForEntity(new URL("http://localhost:" + port + "/api/users/1?name=Leanne Graham").toString(), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), "{\n" +
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
    void dataBase() throws MalformedURLException {
        restTemplate = new TestRestTemplate("admin", "admin");
        base = new URL("http://localhost:" + port);

        ResponseEntity<String> response =
                restTemplate.getForEntity(new URL("http://localhost:" + port + "/api/audits").toString(), String.class);

        System.err.println(response.getBody());

    }
}