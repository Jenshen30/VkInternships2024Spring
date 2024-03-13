package com.vkinterships.VkTask.database;

import java.sql.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class test {
    public static void main(String[] args) {
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
                    "  ('I', 'realUrl','"+ test +"');");

            System.out.println("insert!");

            ResultSet rs = stmt.executeQuery("SELECT * FROM audits");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("url");
                String name1 = rs.getString("USER_NAME");
                Timestamp d = rs.getTimestamp("created_at");
                System.out.println("id: " + id + ", url: " + name + d + name1);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
