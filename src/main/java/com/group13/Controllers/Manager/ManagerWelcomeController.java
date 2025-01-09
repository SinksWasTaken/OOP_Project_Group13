package com.group13.Controllers.Manager;

import com.group13.Models.Model;
import com.group13.Models.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerWelcomeController implements Initializable {

    @FXML
    Label welcome_label_manager;

    @FXML
    Label username_role_manager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Worker currentWorker = Model.getInstance().getCurrentWorker();
        if (currentWorker != null) {
            username_role_manager.setText(currentWorker.getUsername() + ", " + currentWorker.getRole());
            welcome_label_manager.setText("Welcome, " + currentWorker.getFirstname() + " " + currentWorker.getLastname() + "!");
        }
    }
}
