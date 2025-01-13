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

public class UpdateScheduleController {

    @FXML
    private ComboBox<String> sessionComboBox;

    @FXML
    private DatePicker sessionDatePicker;

    @FXML
    private ComboBox<String> sessionTimePicker;

    @FXML
    private ComboBox<Integer> hallComboBox;

    @FXML
    private Button updateScheduleButton;

    private ObservableList<Session> sessions = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadSessions();
        loadTimeOptions();
        loadHalls();

        sessionComboBox.setOnAction(event -> loadSessionDetails());

        updateScheduleButton.setOnAction(event -> updateSchedule());
    }

    private void loadSessions() {
        String query = "SELECT session_id, movie_id, sessionDate, sessionTime, hall_number FROM sessions";

        try (Connection connection = ConnectionModel.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int sessionId = resultSet.getInt("session_id");
                int movieId = resultSet.getInt("movie_id");
                String sessionDate = resultSet.getString("sessionDate");
                String sessionTime = resultSet.getString("sessionTime");
                int hallNumber = resultSet.getInt("hall_number");

                sessions.add(new Session(sessionId, movieId, sessionDate, sessionTime, hallNumber));
            }

            ObservableList<String> sessionDisplays = FXCollections.observableArrayList();
            for (Session session : sessions) {
                sessionDisplays.add(session.toString());
            }

            sessionComboBox.setItems(sessionDisplays);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTimeOptions() {
        ObservableList<String> timeOptions = FXCollections.observableArrayList();
        for (int hour = 0; hour < 24; hour++) {
            for (int minute = 0; minute < 60; minute += 15) {
                timeOptions.add(String.format("%02d:%02d", hour, minute));
            }
        }
        sessionTimePicker.setItems(timeOptions);
    }

    private void loadHalls() {
        ObservableList<Integer> halls = FXCollections.observableArrayList(1, 2);
        hallComboBox.setItems(halls);
    }

    private void loadSessionDetails() {
        String selectedSession = sessionComboBox.getValue();
        if (selectedSession == null) return;

        // Extract session_id from the selected session
        int sessionId = Integer.parseInt(selectedSession.split(" - ")[0]);

        String query = "SELECT sessionDate, sessionTime, hall_number FROM sessions WHERE session_id = ?";
        try (Connection connection = ConnectionModel.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, sessionId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    sessionDatePicker.setValue(LocalDate.parse(resultSet.getString("sessionDate")));
                    sessionTimePicker.setValue(resultSet.getString("sessionTime"));
                    hallComboBox.setValue(resultSet.getInt("hall_number"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateSchedule() {
        String selectedSession = sessionComboBox.getValue();
        if (selectedSession == null) return;

        int sessionId = Integer.parseInt(selectedSession.split(" - ")[0]);
        LocalDate sessionDate = sessionDatePicker.getValue();
        String sessionTime = sessionTimePicker.getValue();
        Integer hallNumber = hallComboBox.getValue();

        if (sessionDate == null || sessionTime == null || hallNumber == null) {
            System.out.println("All fields are required!");
            return;
        }

        String query = "UPDATE sessions SET sessionDate = ?, sessionTime = ?, hall_number = ? WHERE session_id = ?";

        try (Connection connection = ConnectionModel.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, sessionDate.toString());
            statement.setString(2, sessionTime);
            statement.setInt(3, hallNumber);
            statement.setInt(4, sessionId);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Schedule updated successfully!");
                loadSessions();
            } else {
                System.out.println("Failed to update the schedule.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Session {
        private final int id;
        private final int movieId;
        private final String date;
        private final String time;
        private final int hallNumber;

        public Session(int id, int movieId, String date, String time, int hallNumber) {
            this.id = id;
            this.movieId = movieId;
            this.date = date;
            this.time = time;
            this.hallNumber = hallNumber;
        }

        @Override
        public String toString() {
            return String.format("%d - Movie ID: %d (%s, %s, Hall %d)", id, movieId, date, time, hallNumber);
        }
    }
}