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
import java.sql.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

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

        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection("jdbc:h2:file:~/h2/VKTEST", "user", "password");
            System.out.println("Connection successful!");

            Statement stmt = conn.createStatement();
            //stmt.executeUpdate("DROP TABLE audits;");

//            stmt.executeUpdate("CREATE TABLE audits (\n" +
//                    "  id BIGINT AUTO_INCREMENT NOT NULL,\n" +
//                    "   USER_NAME VARCHAR(255) NOT NULL,\n" +
//                    "   url VARCHAR(255) NOT NULL,\n" +
//                    "   created_at timestamp NOT NULL,\n" +
//                    "   CONSTRAINT pk_audits PRIMARY KEY (id)\n" +
//                    ");");


            System.out.println("created!");

            OffsetDateTime da = OffsetDateTime.now();
            Timestamp test = Timestamp.valueOf(da.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());

            stmt.executeUpdate("INSERT \n" +
                    "INTO\n" +
                    "  audits\n" +
                    "  (USER_NAME, url, created_at) \n" +
                    "VALUES\n" +
                    "  ('I', 'realUrl','" + test + "');");

            System.out.println("insert!");

            ResultSet rs = stmt.executeQuery("SELECT * FROM audits");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("url");
                String name1 = rs.getString("USER_NAME");
                Timestamp d = rs.getTimestamp("created_at");
                System.out.println("id: " + id + ", url: " + name + d + name1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ResponseEntity<String> response =
                restTemplate.getForEntity(new URL("http://localhost:" + port + "/api/audits").toString(), String.class);

        System.err.println(response.getBody());

    }
}