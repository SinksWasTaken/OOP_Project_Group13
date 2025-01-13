package com.group13.Controllers.Cashier;

import java.net.URL;
import java.util.ResourceBundle;

import com.group13.Models.Model;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class CashierDashboardController implements Initializable {

    public BorderPane cashier_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewManager().getCashierSelectedMenuItem().addListener((observable, oldVal, newVal) -> {
            switch (newVal) {
                case "Search By Genre" -> cashier_parent.setCenter(Model.getInstance().getViewManager().getSearchByGenreView());
                case "Search By Name" -> cashier_parent.setCenter(Model.getInstance().getViewManager().getSearchByNameView());
                case "Search By Partial Name" -> cashier_parent.setCenter(Model.getInstance().getViewManager().getSearchByPartialNameView());
                case "Session Selection" -> cashier_parent.setCenter(Model.getInstance().getViewManager().getSessionSelectionView());
                case "Hall" -> cashier_parent.setCenter(Model.getInstance().getViewManager().getHallView());
                case "Product Selection" -> cashier_parent.setCenter(Model.getInstance().getViewManager().getProductSelectionView());
                case "Purchase Ticket" -> cashier_parent.setCenter(Model.getInstance().getViewManager().getPurchaseView());
                default -> cashier_parent.setCenter(Model.getInstance().getViewManager().getSearchByGenreView());
            }
        });
    }
}
