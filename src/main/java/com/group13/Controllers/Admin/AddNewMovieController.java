package com.group13.Controllers.Admin;

import com.group13.Models.ConnectionModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    private TextField summaryField;

    @FXML
    private TextField posterPathField;

    @FXML
    private Button addMovieButton;

    @FXML
    private Button uploadPosterButton;

    private static final String IMAGES_FOLDER = "Images/";

    @FXML
    public void initialize() {
        addMovieButton.setOnAction(event -> {
            String title = titleField.getText();
            String genre = genreField.getText();
            String summary = summaryField.getText();
            String posterPath = posterPathField.getText();
            addMovie(title, genre, summary, posterPath);
        });

        uploadPosterButton.setOnAction(event -> uploadPoster());
    }

    private void uploadPoster() {
        // Open FileChooser dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.webp"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                // Copy the selected file to the "Images" folder
                File destinationFolder = new File(IMAGES_FOLDER);
                if (!destinationFolder.exists()) {
                    destinationFolder.mkdir(); // Create folder if it doesn't exist
                }

                File destinationFile = new File(IMAGES_FOLDER + selectedFile.getName());
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Update the posterPathField with the relative path
                posterPathField.setText(IMAGES_FOLDER + selectedFile.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
                System.out.println("Movie added successfully!");
            } else {
                System.out.println("Failed to add the movie.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
