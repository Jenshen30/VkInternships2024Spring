package com.vkinterships.VkTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
    public static void main(String[] args) {
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection("jdbc:h2:file:~/h2/VKTEST", "user", "password");
            System.out.println("Connection successful!");

            Statement stmt = conn.createStatement();
            //stmt.executeUpdate("DROP TABLE audits;");

            stmt.executeUpdate("CREATE TABLE audits (\n" +
                    "  id BIGINT AUTO_INCREMENT NOT NULL,\n" +
                    "   USER_NAME VARCHAR(255) NOT NULL,\n" +
                    "   url VARCHAR(255) NOT NULL,\n" +
                    "   created_at timestamp NOT NULL,\n" +
                    "   CONSTRAINT pk_audits PRIMARY KEY (id)\n" +
                    ");");


            System.out.println("created!");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}