package com.vkinterships.VkTask;

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
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class DBTest {

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
    void dataBaseEmpty() throws MalformedURLException {
        restTemplate = new TestRestTemplate("admin", "admin");
        ResponseEntity<String> response =
                restTemplate.getForEntity(new URL("http://localhost:" + port + "/api/audits").toString(), String.class);

        response =
                restTemplate.getForEntity(new URL("http://localhost:" + port + "/api/users").toString(), String.class);

        response =
                restTemplate.getForEntity(new URL("http://localhost:" + port + "/api/audits").toString(), String.class);


        System.err.println(response.getBody());

    }
}

