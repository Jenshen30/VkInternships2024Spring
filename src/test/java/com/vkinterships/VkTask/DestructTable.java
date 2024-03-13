package com.vkinterships.VkTask;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DestructTable {
    public static void main(String[] args) {
        try {
            Class.forName("org.h2.Driver");
            Connection conn =
                    DriverManager.getConnection("jdbc:h2:file:~/h2/VKTEST", "user", "password");
            System.out.println("Connection successful!");

            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DROP TABLE audits;");

            System.out.println("destruct audits table!");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
