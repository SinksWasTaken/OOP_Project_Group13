package com.group13.Controllers;

import com.group13.Models.LoginModel;
import com.group13.Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    private final LoginModel loginModel = new LoginModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signInButton.setOnAction(event -> handleLogin());
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.WARNING, "Validation Error", "Please fill in both username and password fields.");
            return;
        }

        if (loginModel.validateUser(username, password)) {
            String role = loginModel.getUserRole(username);
            if (role != null) {
                navigateToRoleBasedScene(role);
            } else {
                showAlert(AlertType.ERROR, "Login Error", "User role not found.");
            }
        } else {
            showAlert(AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }

    private void navigateToRoleBasedScene(String role) {
        try {
            switch (role.toLowerCase()) {
                case "admin" -> Model.getInstance().getViewManager().showAdminWindow();
                case "manager" -> Model.getInstance().getViewManager().showManagerWindow();
                case "cashier" -> Model.getInstance().getViewManager().showCashierWindow();
                default -> showAlert(AlertType.ERROR, "Role Error", "Unknown role: " + role);
            }
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Navigation Error", "Unable to load the requested scene.");
            e.printStackTrace();
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
