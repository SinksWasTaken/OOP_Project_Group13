package com.group13.Controllers.Admin.MenuOperations;

import com.group13.Models.ConnectionModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class AddNewMovieController {

    @FXML
    private TextField titleField;

    @FXML
    private TextField genreField;

    @FXML
    private TextArea summaryField;

    @FXML
    private Button addMovieButton;

    @FXML
    private Button uploadPosterButton;

    @FXML
    private ImageView movieImageView;

    private String posterPath;

    private String getPosterPath() {
        return posterPath;
    }

    private void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @FXML
    public void initialize() {
        Image defaultImage = new Image(getClass().getResource("/Images/movies/default-image.jpg").toString());
        movieImageView.setImage(defaultImage);

        addMovieButton.setOnAction(event -> {
            if (validateInputs()) {
                String title = titleField.getText();
                String genre = genreField.getText();
                String summary = summaryField.getText();
                String posterPath = getPosterPath();
                addMovie(title, genre, summary, posterPath);
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

                String posterPath = "src/main/resources/Images/movies/" + selectedFile.getName();
                setPosterPath(posterPath);

                Image image = new Image(destFile.toURI().toString());
                movieImageView.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
            showAlert("Invalid image extension. Accepted extensions are: .jpg, .jpeg, .png");
            return false;
        }

        if (isTitleDuplicate(title)) {
            showAlert("A movie with the same title already exists. Please choose a different title.");
            return false;
        }

        return true;
    }

    private boolean isValidImageExtension(String path) {
        String extension = path.substring(path.lastIndexOf(".") + 1).toLowerCase();
        return extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png");
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

    private void addMovie(String title, String genre, String summary, String posterPath) {
        String query = "INSERT INTO movies (movie_name, genre, summary, img_path) VALUES (?, ?, ?, ?)";

        try (var connection = ConnectionModel.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, genre);
            preparedStatement.setString(3, summary);
            preparedStatement.setString(4, posterPath);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showSuccessAlert("Movie added successfully!");
                resetForm();
            } else {
                showAlert("Failed to add the movie.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetForm() {
        titleField.clear();
        genreField.clear();
        summaryField.clear();
        movieImageView.setImage(new Image(getClass().getResource("/Images/movies/default-image.jpg").toString()));
        posterPath = null;
    }
}