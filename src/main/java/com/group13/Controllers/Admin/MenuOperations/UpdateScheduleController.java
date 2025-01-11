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

    @FXML
    public void initialize() {
        loadSessions();
        loadTimeOptions();
        loadHalls();

        sessionComboBox.setOnAction(event -> loadSessionDetails());

        updateScheduleButton.setOnAction(event -> updateSchedule());
    }

    private void loadSessions() {
        ObservableList<String> sessions = FXCollections.observableArrayList();
        String query = "SELECT session_id, movie_name, session_date, session_time, hall_number FROM sessions";

        try (Connection connection = ConnectionModel.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int sessionId = resultSet.getInt("session_id");
                String movieName = resultSet.getString("movie_name");
                String sessionDate = resultSet.getString("session_date");
                String sessionTime = resultSet.getString("session_time");
                int hallNumber = resultSet.getInt("hall_number");

                // Create a display string for the ComboBox
                String sessionDisplay = String.format("%d - %s (%s, %s, Hall %d)", sessionId, movieName, sessionDate, sessionTime, hallNumber);
                sessions.add(sessionDisplay);
            }

            sessionComboBox.setItems(sessions);

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void loadHalls() {
        ObservableList<Integer> halls = FXCollections.observableArrayList(1, 2); // Adjust based on your project
        hallComboBox.setItems(halls);
    }

    private void loadSessionDetails() {
        String selectedSession = sessionComboBox.getValue();
        if (selectedSession == null) return;

        // Extract session_id from the selected session
        int sessionId = Integer.parseInt(selectedSession.split(" - ")[0]);

        String query = "SELECT session_date, session_time, hall_number FROM sessions WHERE session_id = ?";
        try (Connection connection = ConnectionModel.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, sessionId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    sessionDatePicker.setValue(LocalDate.parse(resultSet.getString("session_date")));
                    sessionTimePicker.setValue(resultSet.getString("session_time"));
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

        String query = "UPDATE sessions SET session_date = ?, session_time = ?, hall_number = ? WHERE session_id = ?";

        try (Connection connection = ConnectionModel.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, sessionDate.toString());
            statement.setString(2, sessionTime);
            statement.setInt(3, hallNumber);
            statement.setInt(4, sessionId);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Schedule updated successfully!");
                loadSessions(); // Reload the session list after updating
            } else {
                System.out.println("Failed to update the schedule.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
