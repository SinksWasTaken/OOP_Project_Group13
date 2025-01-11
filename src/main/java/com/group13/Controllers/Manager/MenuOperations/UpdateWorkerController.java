package com.group13.Controllers.Manager.MenuOperations;

import com.group13.Models.ConnectionModel;
import com.group13.Models.Model;
import com.group13.Models.Worker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class UpdateWorkerController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField firstnameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private Button updateWorkerButton;


    @FXML
    public void initialize() {

        Worker selectedWorker = Model.getInstance().getNextWorker();

        int roleID = 0;

        if (selectedWorker != null) {

            loadRoleList();

            roleID = switch (selectedWorker.getRole()) {
                case "Cashier" -> 1;
                case "Admin" -> 2;
                case "Manager" -> 3;
                default -> roleID;
            };

            setWorkerDetails(
                    selectedWorker.getUsername(),
                    selectedWorker.getFirstname(),
                    selectedWorker.getLastname(),
                    roleID
            );
        } else {
            showErrorAlert("No worker selected to update.");
        }

        updateWorkerButton.setOnAction(event -> {

            if (validateInputs()) {
                String username = usernameField.getText();
                String firstname = firstnameField.getText();
                String lastname = lastnameField.getText();
                int role = roleComboBox.getSelectionModel().getSelectedIndex();

                updateWorker(username, firstname, lastname, role + 1);
            }
        });
    }

    private void updateWorker(String username, String firstname, String lastname, int role) {
        String query = "UPDATE workers SET username = ?, firstname = ?, lastname = ?, role = ? WHERE username = ?";

        try (var connection = ConnectionModel.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, firstname);
            preparedStatement.setString(3, lastname);
            preparedStatement.setInt(4, role);
            preparedStatement.setString(5, username);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showSuccessAlert("Worker updated successfully!");
            } else {
                showAlert("Failed to update the worker.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setWorkerDetails (String username, String firstname, String lastname, int role) {
        usernameField.setText(username);
        firstnameField.setText(firstname);
        lastnameField.setText(lastname);
        roleComboBox.getSelectionModel().select(role);
    }

    private boolean validateInputs() {
        String username = usernameField.getText();
        String firstname = firstnameField.getText();
        String lastname = lastnameField.getText();
        String role = roleComboBox.getSelectionModel().getSelectedItem();
        Worker selectedWorker = Model.getInstance().getNextWorker();

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

        if((!Objects.equals(selectedWorker.getUsername(), username)) && isUsernameDuplicate(username)) {
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

    private void showErrorAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An Error Occurred");
        alert.setContentText(content);
        alert.showAndWait();
    }


}
