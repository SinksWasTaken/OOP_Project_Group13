package com.group13.Controllers.Admin;

import com.group13.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class    AdminOperation1Controller {

    @FXML
    private TextField titleField;

    @FXML
    private TextField genreField;

    @FXML
    private TextField summaryField;

    @FXML
    private TextField posterPathField;

    @FXML
    private Button addMovieButton;

    @FXML
    public void initialize() {
        // Add a button action handler
        addMovieButton.setOnAction(event -> {
            // Get the values from TextFields
            String title = titleField.getText();
            String genre = genreField.getText();
            String summary = summaryField.getText();
            String posterPath = posterPathField.getText();

            // Call the method to add the movie to the database
            addMovie(title, genre, summary, posterPath);
        });
    }

    private void addMovie(String title, String genre, String summary, String posterPath) {
        if (title == null || title.isEmpty()) {
            System.out.println("Title cannot be empty.");
            return;
        }

        // SQL query to insert a new movie
        String query = "INSERT INTO movies (movie_name, genre, summary, img_path) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, genre);
            preparedStatement.setString(3, summary);
            preparedStatement.setString(4, posterPath);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Movie added successfully!");
            } else {
                System.out.println("Failed to add the movie.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
