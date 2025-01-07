package com.group13.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionModel {
    private static final String URL = "jdbc:mysql://localhost:3306/group13";
    private static final String USER = "admin1";
    private static final String PASSWORD = "admin1";

    private ConnectionModel() {

    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            throw new RuntimeException("Unable to establish a database connection.");
        }
    }
}
