package com.group13.Controllers.Admin;

import com.group13.Models.ConnectionModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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

    @FXML
    private Button uploadPosterButton;

    private static final String IMAGES_FOLDER = "Images/";

    @FXML
    public void initialize() {
        loadMovieList();

        movieComboBox.setOnAction(event -> loadMovieDetails());

        updateMovieButton.setOnAction(event -> {
            String selectedMovie = movieComboBox.getSelectionModel().getSelectedItem();
            if (selectedMovie != null) {
                updateMovie();
            }
        });

        uploadPosterButton.setOnAction(event -> uploadPoster());
    }

    private void loadMovieList() {
        ObservableList<String> movieNames = FXCollections.observableArrayList();
        String query = "SELECT movie_name FROM movies";

        try (Connection connection = ConnectionModel.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                movieNames.add(resultSet.getString("movie_name"));
            }
            movieComboBox.setItems(movieNames);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadMovieDetails() {
        String selectedMovie = movieComboBox.getSelectionModel().getSelectedItem();
        if (selectedMovie == null) return;

        String query = "SELECT * FROM movies WHERE movie_name = ?";
        try (var connection = ConnectionModel.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, selectedMovie);

            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    titleField.setText(resultSet.getString("movie_name"));
                    genreField.setText(resultSet.getString("genre"));
                    summaryField.setText(resultSet.getString("summary"));
                    posterPathField.setText(resultSet.getString("img_path"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateMovie() {
        String selectedMovie = movieComboBox.getSelectionModel().getSelectedItem();
        String title = titleField.getText();
        String genre = genreField.getText();
        String summary = summaryField.getText();
        String posterPath = posterPathField.getText();

        String query = "UPDATE movies SET movie_name = ?, genre = ?, summary = ?, img_path = ? WHERE movie_name = ?";

        try (var connection = ConnectionModel.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, genre);
            preparedStatement.setString(3, summary);
            preparedStatement.setString(4, posterPath);
            preparedStatement.setString(5, selectedMovie);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Movie updated successfully!");
                loadMovieList(); // Reload updated movie list
            } else {
                System.out.println("Failed to update the movie.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadPoster() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.webp"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                File destinationFolder = new File(IMAGES_FOLDER);
                if (!destinationFolder.exists()) {
                    destinationFolder.mkdir(); // Create folder if it doesn't exist
                }

                File destinationFile = new File(IMAGES_FOLDER + selectedFile.getName());
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                posterPathField.setText(IMAGES_FOLDER + selectedFile.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
