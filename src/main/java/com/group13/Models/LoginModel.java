package com.group13.Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {

    public boolean validateUser(String username, String password) {
        String query = "SELECT password FROM workers WHERE username = ?";
        try (Connection conn = ConnectionModel.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String dbPassword = rs.getString("password");
                return dbPassword.equals(password);
            }
        } catch (SQLException e) {
            System.err.println("Error during user validation: " + e.getMessage());
        }
        return false;
    }

    public Worker getUser(String username) {
        String query = "SELECT w.username, w.firstname, w.lastname, r.role_name FROM workers w INNER JOIN roles r ON w.role = r.role_id WHERE w.username = ?";
        try (Connection conn = ConnectionModel.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String role = rs.getString("role_name");

                return new Worker(username, firstname, lastname, role);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user role: " + e.getMessage());
        }
        return null;
    }
}
