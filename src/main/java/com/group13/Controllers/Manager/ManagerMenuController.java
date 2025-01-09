package com.group13.Controllers.Manager;

import com.group13.Controllers.LogoutController;
import com.group13.Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerMenuController implements Initializable {

    @FXML
    public javafx.scene.control.Button home_btn;

    @FXML
    public javafx.scene.control.Button inventory_btn;

    @FXML
    public javafx.scene.control.Button price_btn;

    @FXML
    public javafx.scene.control.Button revenueTax_btn;

    @FXML
    public javafx.scene.control.Button worker_btn;

    @FXML
    public javafx.scene.control.Button logout_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        home_btn.setOnAction(event -> onHome());
        inventory_btn.setOnAction(event -> onInventory());
        price_btn.setOnAction(event -> onPrice());
        revenueTax_btn.setOnAction(event -> onRevenueTax());
        worker_btn.setOnAction(event -> onWorker());
        logout_btn.setOnAction(event -> onLogout());
    }

    private void onHome() {
        Model.getInstance().getViewManager().getManagerSelectedMenuItem().set("Home");
    }

    private void onInventory() {
        Model.getInstance().getViewManager().getManagerSelectedMenuItem().set("Inventory");

    }

    private void onPrice() {
        Model.getInstance().getViewManager().getManagerSelectedMenuItem().set("Price");

    }

    private void onRevenueTax() {
        Model.getInstance().getViewManager().getManagerSelectedMenuItem().set("Revenue Tax");

    }

    private void onWorker() {
        Model.getInstance().getViewManager().getManagerSelectedMenuItem().set("Worker");

    }

    private void onLogout() {
        LogoutController logoutController = new LogoutController();
        logoutController.handleLogout();
    }
}
