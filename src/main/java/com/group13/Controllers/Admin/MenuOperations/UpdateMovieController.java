package com.group13.Controllers.Admin.MenuOperations;

import com.group13.Models.ConnectionModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private TextArea summaryField;

    @FXML
    private Button updateMovieButton;

    @FXML
    private ImageView movieImageView;

    @FXML
    private Button uploadPosterButton;

    @FXML
    private Button deleteMovieButton;

    private String posterPath;
    private String originalTitle;

    private String getPosterPath() {
        return posterPath;
    }

    private void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @FXML
    public void initialize() {
        loadMovieList();
        Image defaultImage = new Image(getClass().getResource("/Images/movies/default-image.jpg").toString());
        movieImageView.setImage(defaultImage);

        updateMovieButton.setDisable(true);
        uploadPosterButton.setDisable(true);
        deleteMovieButton.setDisable(true);

        movieComboBox.setOnAction(event -> {
            loadMovieDetails();

            boolean isMovieSelected = movieComboBox.getSelectionModel().getSelectedItem() != null;
            updateMovieButton.setDisable(!isMovieSelected);
            uploadPosterButton.setDisable(!isMovieSelected);
            deleteMovieButton.setDisable(!isMovieSelected);
        });

        updateMovieButton.setOnAction(event -> {
            String selectedMovie = movieComboBox.getSelectionModel().getSelectedItem();
            if (selectedMovie != null) {
                if (validateInputs()) {
                    updateMovie();
                }
            }
        });

        deleteMovieButton.setOnAction(event -> {
            String selectedMovie = movieComboBox.getSelectionModel().getSelectedItem();
            if (selectedMovie != null) {
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirmation");
                confirmationAlert.setHeaderText(null);
                confirmationAlert.setContentText("Are you sure you want to delete the movie: " + selectedMovie + "?");

                confirmationAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        deleteMovie(selectedMovie);
                    }
                });
            }
        });

        uploadPosterButton.setOnAction(event -> uploadImage());
    }

    @FXML
    public void uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.webp"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            File destFile = new File("src/main/resources/Images/" + selectedFile.getName());
            if (destFile.exists()) {
                showAlert("An image with the same name already exists. Please rename the file and try again.");
                return;
            }

            try {
                Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                String posterPath = "src/main/resources/Images/" + selectedFile.getName();
                setPosterPath(posterPath);

                Image image = new Image(destFile.toURI().toString());
                movieImageView.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadMovieList() {
        ObservableList<String> movieNames = FXCollections.observableArrayList();
        String query = "SELECT movie_name FROM movies ORDER BY movie_name ASC";

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
                    originalTitle = resultSet.getString("movie_name");
                    titleField.setText(originalTitle);
                    genreField.setText(resultSet.getString("genre"));
                    summaryField.setText(resultSet.getString("summary"));

                    String imgPath = resultSet.getString("img_path");
                    setPosterPath(imgPath);

                    File imgFile = new File(imgPath);
                    if (imgFile.exists()) {
                        movieImageView.setImage(new Image(imgFile.toURI().toString()));
                    } else {
                        System.out.println("Image file not found: " + imgPath);
                    }
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
        String posterPath = getPosterPath();

        if (!title.equals(originalTitle) && isTitleDuplicate(title)) {
            showAlert("A movie with the same title already exists. Please choose a different title.");
            return;
        }

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
                showSuccessAlert("Movie updated successfully!");
                resetForm();
                loadMovieList();
            } else {
                showAlert("Failed to update the movie.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteMovie(String movieName) {
        String query = "DELETE FROM movies WHERE movie_name = ?";

        try (var connection = ConnectionModel.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, movieName);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showSuccessAlert("Movie deleted successfully!");
                resetForm();
                loadMovieList();
            } else {
                showAlert("Failed to delete the movie.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isTitleDuplicate(String title) {
        String query = "SELECT COUNT(*) FROM movies WHERE movie_name = ?";
        try (var connection = ConnectionModel.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, title);

            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean validateInputs() {
        String title = titleField.getText();
        String genre = genreField.getText();
        String summary = summaryField.getText();

        if (title.isEmpty() || title.length() > 50) {
            showAlert("Title must be between 1 and 50 characters.");
            return false;
        }

        if (genre.isEmpty() || genre.length() > 20) {
            showAlert("Genre must be between 1 and 20 characters.");
            return false;
        }

        if (summary.isEmpty() || summary.length() > 1000) {
            showAlert("Summary must be between 1 and 1000 characters.");
            return false;
        }

        if (posterPath == null || posterPath.isEmpty()) {
            showAlert("Please upload a poster image.");
            return false;
        }

        if (!isValidImageExtension(posterPath)) {
            showAlert("Invalid image extension. Accepted extensions are: .jpg, .jpeg, .png, .webp");
            return false;
        }

        return true;
    }

    private boolean isValidImageExtension(String path) {
        String extension = path.substring(path.lastIndexOf(".") + 1).toLowerCase();
        return extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("webp");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void resetForm() {
        movieComboBox.getSelectionModel().clearSelection();
        titleField.clear();
        genreField.clear();
        summaryField.clear();
        movieImageView.setImage(new Image(getClass().getResource("/Images/movies/default-image.jpg").toString()));
        setPosterPath(null);
    }
}
