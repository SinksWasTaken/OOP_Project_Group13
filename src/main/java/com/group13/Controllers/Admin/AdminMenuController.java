package com.group13.Controllers.Admin;

import com.group13.Controllers.LogoutController;
import com.group13.Models.Model;
import com.group13.Models.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {

    @FXML
    public javafx.scene.control.Button home_btn;

    @FXML
    public javafx.scene.control.Button addNewMovie_btn;

    @FXML
    public javafx.scene.control.Button updateMovie_btn;

    @FXML
    public javafx.scene.control.Button createSchedule_btn;

    @FXML
    public javafx.scene.control.Button updateSchedule_btn;

    @FXML
    public javafx.scene.control.Button customerRequest_btn;

    @FXML
    public javafx.scene.control.Button logout_btn;

    @FXML
    private Label username_admin;

    @FXML
    private Label role_admin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Worker currentWorker = Model.getInstance().getCurrentWorker();
        if (currentWorker != null) {
            username_admin.setText("Username: " + currentWorker.getUsername());
            role_admin.setText("Role: " + currentWorker.getRole());
        }
        addListeners();
    }

    private void addListeners() {
        home_btn.setOnAction(event -> onHome());
        addNewMovie_btn.setOnAction(event -> onAddNewMovie());
        updateMovie_btn.setOnAction(event -> onUpdateMovie());
        createSchedule_btn.setOnAction(event -> onCreateSchedule());
        updateSchedule_btn.setOnAction(event -> onUpdateSchedule());
        customerRequest_btn.setOnAction(event -> onCustomerRequest());
        logout_btn.setOnAction(event -> onLogout());
    }

    private void onHome() {
        Model.getInstance().getViewManager().getAdminSelectedMenuItem().set("Home");
    }

    private void onAddNewMovie() {
        Model.getInstance().getViewManager().getAdminSelectedMenuItem().set("Add New Movie");
    }

    private void onUpdateMovie() {
        Model.getInstance().getViewManager().getAdminSelectedMenuItem().set("Update Movie");
    }

    private void onCreateSchedule() {
        Model.getInstance().getViewManager().getAdminSelectedMenuItem().set("Create Schedule");
    }

    private void onUpdateSchedule() {
        Model.getInstance().getViewManager().getAdminSelectedMenuItem().set("Update Schedule");
    }

    private void onCustomerRequest() {
        Model.getInstance().getViewManager().getAdminSelectedMenuItem().set("Customer Request");
    }

    private void onLogout() {
        LogoutController logoutController = new LogoutController();
        logoutController.handleLogout();
    }

}
