package com.group13.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpdateMovieController {

    @FXML
    private ComboBox<String> movieComboBox;

    @FXML
    private TextField titleField;

    @FXML
    private TextField genreField;

    @FXML
    private TextField summaryField;

    @FXML
    private TextField posterPathField;

    @FXML
    private Button updateMovieButton;

    private final ObservableList<String> movieList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadMovieList();

        // Populate fields when a movie is selected
        movieComboBox.setOnAction(event -> {
            String selectedMovie = movieComboBox.getValue();
            if (selectedMovie != null) {
                populateFields(selectedMovie);
            }
        });

        // Update the movie in the database
        updateMovieButton.setOnAction(event -> {
            updateMovie();
        });
    }

    private void loadMovieList() {
        movieList.clear(); // Clear the list before fetching new data
        try (Connection connection = com.group13.Models.ConnectionModel.getConnection()) {
            String query = "SELECT movie_name FROM movies";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                movieList.add(resultSet.getString("movie_name"));
            }
            movieComboBox.setItems(movieList); // Set the list in the ComboBox
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateFields(String movieName) {
        try (Connection connection = com.group13.Models.ConnectionModel.getConnection()) {
            String query = "SELECT * FROM movies WHERE movie_name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, movieName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                titleField.setText(resultSet.getString("movie_name"));
                genreField.setText(resultSet.getString("genre"));
                summaryField.setText(resultSet.getString("summary"));
                posterPathField.setText(resultSet.getString("img_path"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateMovie() {
        String selectedMovie = movieComboBox.getValue();
        if (selectedMovie != null) {
            try (Connection connection = com.group13.Models.ConnectionModel.getConnection()) {
                String query = "UPDATE movies SET movie_name = ?, genre = ?, summary = ?, img_path = ? WHERE movie_name = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, titleField.getText());
                statement.setString(2, genreField.getText());
                statement.setString(3, summaryField.getText());
                statement.setString(4, posterPathField.getText());
                statement.setString(5, selectedMovie);
                statement.executeUpdate();

                System.out.println("Movie updated successfully!");
                loadMovieList(); // Reload
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
