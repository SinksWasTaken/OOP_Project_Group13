package com.group13.Controllers.Admin;

import com.group13.Models.ConnectionModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpdateMovieController {

    @FXML
    private ComboBox<String> movieSelector;

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

    @FXML
    public void initialize() {
        populateMovieSelector();

        movieSelector.setOnAction(event -> loadMovieDetails(movieSelector.getValue()));
        updateMovieButton.setOnAction(event -> {
            String originalMovie = movieSelector.getValue();
            String title = titleField.getText();
            String genre = genreField.getText();
            String summary = summaryField.getText();
            String posterPath = posterPathField.getText();
            updateMovie(originalMovie, title, genre, summary, posterPath);
        });
    }

    private void populateMovieSelector() {
        String query = "SELECT movie_name FROM movies";

        try (Connection connection = ConnectionModel.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                movieSelector.getItems().add(resultSet.getString("movie_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadMovieDetails(String movieName) {
        String query = "SELECT * FROM movies WHERE movie_name = ?";

        try (Connection connection = ConnectionModel.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, movieName);
            ResultSet resultSet = preparedStatement.executeQuery();

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

    private void updateMovie(String originalName, String newTitle, String newGenre, String newSummary, String newPosterPath) {
        String query = "UPDATE movies SET movie_name = ?, genre = ?, summary = ?, img_path = ? WHERE movie_name = ?";

        try (Connection connection = ConnectionModel.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newTitle);
            preparedStatement.setString(2, newGenre);
            preparedStatement.setString(3, newSummary);
            preparedStatement.setString(4, newPosterPath);
            preparedStatement.setString(5, originalName);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Movie updated successfully!");
                movieSelector.getItems().clear();
                populateMovieSelector();
            } else {
                System.out.println("Failed to update the movie.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
