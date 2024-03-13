package com.vkinterships.VkTask.database;

import java.sql.*;

public class test {
    public static void main(String[] args) {
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection("jdbc:h2:file:~/h2/VKTEST", "user", "password");
            System.out.println("Connection successful!");

            Statement stmt = conn.createStatement();
//            stmt.executeUpdate("CREATE TABLE audits (\n" +
//                    "  id BIGINT AUTO_INCREMENT NOT NULL,\n" +
//                    "   userName VARCHAR(255) NOT NULL,\n" +
//                    "   url VARCHAR(255) NOT NULL,\n" +
//                    "   created_at Date NOT NULL,\n" +
//                    "   CONSTRAINT pk_audits PRIMARY KEY (id)\n" +
//                    ");");



            System.out.println("created!");

            Date da = new Date(2021);

            stmt.executeUpdate("INSERT \n" +
                    "INTO\n" +
                    "  audits\n" +
                    "  (userName, url, created_at) \n" +
                    "VALUES\n" +
                    "  ('I', 'realUrl',"+ da +");");

            System.out.println("insert!");

            ResultSet rs = stmt.executeQuery("SELECT * FROM audits");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("url");
                Date d = rs.getDate("current_date");
                System.out.println("id: " + id + ", url: " + name + d);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
