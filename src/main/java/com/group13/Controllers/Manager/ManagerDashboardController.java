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
                case "Inventory" -> manager_parent.setCenter(Model.getInstance().getViewManager().getInventoryView());
                case "Add Product" -> manager_parent.setCenter(Model.getInstance().getViewManager().getAddProductView());
                case "Update Product" -> manager_parent.setCenter(Model.getInstance().getViewManager().getUpdateProductView());
                case "Price" -> manager_parent.setCenter(Model.getInstance().getViewManager().getPriceView());
                case "Revenue Tax" -> manager_parent.setCenter(Model.getInstance().getViewManager().getRevenueTaxView());
                case "Worker" -> manager_parent.setCenter(Model.getInstance().getViewManager().getWorkerView());
                case "Add Worker" -> manager_parent.setCenter(Model.getInstance().getViewManager().getAddWorkerView());
                case "Update Worker" -> manager_parent.setCenter(Model.getInstance().getViewManager().getUpdateWorkerView());

                default -> manager_parent.setCenter(Model.getInstance().getViewManager().getInventoryView());
            }
        });
    }
}
