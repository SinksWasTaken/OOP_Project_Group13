package com.group13.Controllers.Manager.MenuOperations;

import com.group13.Models.ConnectionModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddWorkerController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField firstnameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private Button addWorkerButton;

    @FXML
    public void initialize() {

        loadRoleList();

        addWorkerButton.setOnAction(event -> {
            if (validateInputs()) {
                String username = usernameField.getText();
                String firstname = firstnameField.getText();
                String lastname = lastnameField.getText();
                int role = roleComboBox.getSelectionModel().getSelectedIndex();

                addWorker(username, firstname, lastname, role);
            }
        });
    }

    private void addWorker(String username, String firstname, String lastname, int role) {
        final String defaultPassword = "khas123";
        String query = "INSERT INTO workers (username, password, firstname, lastname, role) VALUES (?, ?, ?, ?, ?)";

        try (var connection = ConnectionModel.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, defaultPassword);
            preparedStatement.setString(3, firstname);
            preparedStatement.setString(4, lastname);
            preparedStatement.setInt(5, role);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                showSuccessAlert("Worker added successfully! The password of the created worker is khas1234. Please inform the worker.");
                resetForm();
            } else {
                showAlert("Failed to add the Worker.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadRoleList() {
        ObservableList<String> roleNames = FXCollections.observableArrayList();
        String query = "SELECT role_name FROM roles ORDER BY role_name ASC";

        try (Connection connection = ConnectionModel.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                roleNames.add(resultSet.getString("role_name"));
            }
            roleComboBox.setItems(roleNames);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateInputs() {
        String username = usernameField.getText();
        String firstname = firstnameField.getText();
        String lastname = lastnameField.getText();
        String role = roleComboBox.getSelectionModel().getSelectedItem();

        if (username.isEmpty() || username.length() > 50) {
            showAlert("Worker username must be between 1 and 50 characters.");
            return false;
        }

        if (firstname.isEmpty()) {
            showAlert("First name cannot be empty.");
            return false;
        }

        if (lastname.isEmpty()) {
            showAlert("Last name cannot be empty.");
            return false;
        }

        if (role.isEmpty()) {
            showAlert("Role cannot be empty.");
        }

        if (isUsernameDuplicate(username)) {
            showAlert("Username name with the same name already exists. Please choose a different username.");
            return false;
        }

        return true;
    }

    private boolean isUsernameDuplicate(String username) {
        String query = "SELECT COUNT(*) FROM workers WHERE username = ?";
        try (var connection = ConnectionModel.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);

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

    private void resetForm() {
        usernameField.clear();
        firstnameField.clear();
        lastnameField.clear();
        roleComboBox.getItems().clear();
    }

}
