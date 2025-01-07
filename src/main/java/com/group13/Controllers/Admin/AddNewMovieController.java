package com.group13.Controllers.Admin;

import com.group13.Models.ConnectionModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
    public void initialize() {
        addMovieButton.setOnAction(event -> {
            String title = titleField.getText();
            String genre = genreField.getText();
            String summary = summaryField.getText();
            String posterPath = posterPathField.getText();
            addMovie(title, genre, summary, posterPath);
        });
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
