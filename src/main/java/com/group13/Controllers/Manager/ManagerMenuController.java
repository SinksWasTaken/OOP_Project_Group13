package com.group13.Controllers.Manager;

import com.group13.Controllers.LogoutController;
import com.group13.Models.Model;
import com.group13.Models.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerMenuController implements Initializable {

    @FXML
    public Label username_manager;

    @FXML
    public Label role_manager;

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
        Worker currentWorker = Model.getInstance().getCurrentWorker();
        if (currentWorker != null) {
            username_manager.setText("Username: " + currentWorker.getUsername());
            role_manager.setText("Role: " + currentWorker.getRole());
        }

        addListeners();
    }

    private void addListeners() {
        inventory_btn.setOnAction(event -> onInventory());
        price_btn.setOnAction(event -> onPrice());
        revenueTax_btn.setOnAction(event -> onRevenueTax());
        worker_btn.setOnAction(event -> onWorker());
        logout_btn.setOnAction(event -> onLogout());
    }

    private void onInventory() {
        Model.getInstance().getViewManager().getManagerSelectedMenuItem().set("Inventory");
        Model.getInstance().getViewManager().getInventoryView();

    }

    private void onPrice() {
        Model.getInstance().getViewManager().getManagerSelectedMenuItem().set("Price");
        Model.getInstance().getViewManager().getPriceView();

    }

    private void onRevenueTax() {
        Model.getInstance().getViewManager().getManagerSelectedMenuItem().set("Revenue Tax");
        Model.getInstance().getViewManager().getRevenueTaxView();

    }

    private void onWorker() {
        Model.getInstance().getViewManager().getManagerSelectedMenuItem().set("Worker");
        Model.getInstance().getViewManager().getWorkerView();

    }

    private void onLogout() {
        LogoutController logoutController = new LogoutController();
        logoutController.handleLogout();
    }
}
