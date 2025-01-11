package com.group13.Controllers.Admin;

import com.group13.Models.ConnectionModel;
import com.group13.Models.Model;
import com.group13.Models.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AdminWelcomeController implements Initializable {

    @FXML
    private Label movieCount;

    @FXML
    private Label price;

    @FXML
    private Label customerRequestCount;

    @FXML
    private Label welcome_label_admin;

    @FXML
    private HBox movieCarousel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Worker currentWorker = Model.getInstance().getCurrentWorker();

        if (currentWorker != null) {
            welcome_label_admin.setText("Welcome, " + currentWorker.getFirstname() + " " + currentWorker.getLastname() + "!");
            movieCount.setText(String.valueOf(getMovieCount()));
            price.setText("200₺");
            customerRequestCount.setText("15");
        }
        loadMovies();
    }

    private int getMovieCount() {
        String query = "SELECT COUNT(*) FROM movies";
        int movieCount = 0;

        try (Connection connection = ConnectionModel.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                movieCount = resultSet.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movieCount;
    }

    private int getPrice() {
        return 0;
    }

    private int getCustomerRequestCount() {
        return 0;
    }

    private void loadMovies() {
        String query = "SELECT * FROM movies ORDER BY movie_name ASC";

        try (Connection connection = ConnectionModel.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String title = resultSet.getString("movie_name");
                String genre = resultSet.getString("genre");
                String imgPath = resultSet.getString("img_path");

                VBox movieBox = new VBox();
                movieBox.getStyleClass().add("movie-box");

                Label titleLabel = new Label(title);
                titleLabel.getStyleClass().add("movie-title");

                Label genreLabel = new Label(genre);
                genreLabel.getStyleClass().add("movie-genre");

                ImageView movieImageView = new ImageView();
                movieImageView.setFitHeight(150);
                movieImageView.setFitWidth(120);
                File imgFile = new File(imgPath);
                if (imgFile.exists()) {
                    movieImageView.setImage(new Image(imgFile.toURI().toString()));
                } else {
                    System.out.println("Image file not found: " + imgPath);
                }

                movieBox.getChildren().addAll(movieImageView, titleLabel, genreLabel);
                movieCarousel.getChildren().add(movieBox);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
