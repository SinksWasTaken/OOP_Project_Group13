package com.group13.Controllers.Admin;

import com.group13.Models.Model;
import com.group13.Models.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminWelcomeController implements Initializable {

    @FXML
    private Label username_role;

    @FXML
    private Label welcome_label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Worker currentWorker = Model.getInstance().getCurrentWorker();
        if (currentWorker != null) {
            username_role.setText(currentWorker.getUsername() + ", " + currentWorker.getRole());
            welcome_label.setText("Welcome, " + currentWorker.getFirstname() + " " + currentWorker.getLastname() + "!");
        }
    }
}
