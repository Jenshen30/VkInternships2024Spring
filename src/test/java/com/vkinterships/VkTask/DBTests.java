package com.vkinterships.VkTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.sql.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class DBTests {

    TestRestTemplate restTemplate;
    URL base;

    @LocalServerPort
    int port;

    static Statement stmt;

    @BeforeAll
    static void beforeAll() {
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection("jdbc:h2:file:~/h2/VKTEST", "user", "password");
            System.out.println("Connection successful!");

            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE  FROM audits;");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    void dataBaseSimpleCheck() throws MalformedURLException {
        restTemplate = new TestRestTemplate("admin", "admin");
        String userURL = "http://localhost:" + port + "/api/users";

        ResponseEntity<String> response;
        response =
                restTemplate.getForEntity(
                        new URL("http://localhost:" + port + "/api/users").toString(), String.class);
        response =
                restTemplate.getForEntity(
                        new URL("http://localhost:" + port + "/api/audits").toString(), String.class);

        checkIfThisPersonInResponse("admin", userURL, response);
    }

    @Test
    void checkingThatAllEntriesInDB() throws MalformedURLException {
        restTemplate = new TestRestTemplate("admin", "admin");
        String basicURL = "http://localhost:" + port + "/api/users";

        ResponseEntity<String> response;
        for (int i = 1; i < 11; i++) {
            response =
                    restTemplate.getForEntity(
                            new URL(basicURL + "/" + i).toString(), String.class);
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        response =
                restTemplate.getForEntity(
                        new URL("http://localhost:" + port + "/api/audits").toString(), String.class);

        for (int i = 1; i < 11; i++) {
            checkIfThisPersonInResponse("admin", basicURL + i, response);
        }

    }

    private boolean checkIfThisPersonInResponse(String userLogin, String userUrl, ResponseEntity<String> response) {

        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Map<String, String>> allEntries = getAllEntryInResponse(response);

        for (var kv : allEntries) {
            if (kv.containsValue(userLogin) && kv.containsValue(userUrl)) {
                return true;
            }
        }
        return false;
    }

    private List<Map<String, String>> getAllEntryInResponse(ResponseEntity<String> response) {
        List<Map<String, String>> l = new ArrayList<>();
        List<Map<String, String>> gson = new Gson().fromJson(response.getBody(), (Type) l.getClass());
        return gson;
    }
}

