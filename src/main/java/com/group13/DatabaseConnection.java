package com.group13;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/Group13";
    private static final String USER = "admin1";
    private static final String PASSWORD = "admin1";

    // Method to establish and return a database connection
    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Return null if the connection fails
        }
    }
}
