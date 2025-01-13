package com.group13.Controllers.Admin.MenuOperations;

import com.group13.Models.ConnectionModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class CreateScheduleController {

    @FXML
    private ComboBox<String> movieComboBox;

    @FXML
    private DatePicker sessionDatePicker;

    @FXML
    private ComboBox<String> sessionTimePicker;

    @FXML
    private ComboBox<Integer> hallComboBox;

    @FXML
    private Button addScheduleButton;

    private ObservableList<Movie> movies = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadMovies();
        loadHalls();
        loadTimeOptions();

        addScheduleButton.setOnAction(event -> addSchedule());
    }

    private void loadMovies() {
        String query = "SELECT movie_id, movie_name FROM movies";

        try (Connection connection = ConnectionModel.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int movieId = resultSet.getInt("movie_id");
                String movieName = resultSet.getString("movie_name");
                movies.add(new Movie(movieId, movieName));
            }

            ObservableList<String> movieNames = FXCollections.observableArrayList();
            for (Movie movie : movies) {
                movieNames.add(movie.getName());
            }

            movieComboBox.setItems(movieNames);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadHalls() {
        ObservableList<Integer> halls = FXCollections.observableArrayList(1, 2); // Adjust based on your project
        hallComboBox.setItems(halls);
    }

    private void loadTimeOptions() {
        ObservableList<String> timeOptions = FXCollections.observableArrayList();
        for (int hour = 0; hour < 24; hour++) {
            for (int minute = 0; minute < 60; minute += 15) { // Time intervals of 15 minutes
                timeOptions.add(String.format("%02d:%02d", hour, minute));
            }
        }
        sessionTimePicker.setItems(timeOptions);
    }

    private void addSchedule() {
        String selectedMovieName = movieComboBox.getValue();
        LocalDate sessionDate = sessionDatePicker.getValue();
        String sessionTime = sessionTimePicker.getValue();
        Integer hallNumber = hallComboBox.getValue();

        if (selectedMovieName == null || sessionDate == null || sessionTime == null || hallNumber == null) {
            System.out.println("All fields are required!");
            return;
        }

        int selectedMovieId = -1;
        for (Movie movie : movies) {
            if (movie.getName().equals(selectedMovieName)) {
                selectedMovieId = movie.getId();
                break;
            }
        }

        if (selectedMovieId == -1) {
            System.out.println("Selected movie not found!");
            return;
        }

        String query = "INSERT INTO sessions (sessionDate, sessionTime, hall_number, movie_id) VALUES (?, ?, ?, ?)";

        try (Connection connection = ConnectionModel.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, sessionDate.toString());
            statement.setString(2, sessionTime);
            statement.setInt(3, hallNumber);
            statement.setInt(4, selectedMovieId);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Schedule added successfully!");
            } else {
                System.out.println("Failed to add the schedule.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Movie {
        private final int id;
        private final String name;

        public Movie(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}