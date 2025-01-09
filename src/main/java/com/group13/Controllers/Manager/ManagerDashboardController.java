package com.group13.Controllers.Manager;

import com.group13.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerDashboardController implements Initializable {

    public BorderPane manager_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewManager().getManagerSelectedMenuItem().addListener((observable, oldVal, newVal) -> {
            switch (newVal) {
                case "Home" -> manager_parent.setCenter(Model.getInstance().getViewManager().getManagerWelcomeView());
                case "Inventory" -> manager_parent.setCenter(Model.getInstance().getViewManager().getInventoryView());
                case "Price" -> manager_parent.setCenter(Model.getInstance().getViewManager().getPriceView());
                case "Revenue Tax" -> manager_parent.setCenter(Model.getInstance().getViewManager().getRevenueTaxView());
                case "Worker" -> manager_parent.setCenter(Model.getInstance().getViewManager().getWorkerView());
                default -> manager_parent.setCenter(Model.getInstance().getViewManager().getManagerWelcomeView());
            }
        });
    }
}
