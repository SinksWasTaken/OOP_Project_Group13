package com.group13.Controllers.Cashier;

import com.group13.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CashierDashboardController implements Initializable {

    public BorderPane cashier_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewManager().getCashierSelectedMenuItem().addListener((observable, oldVal, newVal) -> {
            switch (newVal) {
                case "Home" -> cashier_parent.setCenter(Model.getInstance().getViewManager().getCashierWelcomeView());
                case "Search By Genre" -> cashier_parent.setCenter(Model.getInstance().getViewManager().getSearchByGenreView());
                case "Search By Name" -> cashier_parent.setCenter(Model.getInstance().getViewManager().getSearchByNameView());
                case "Search By Partial Name" -> cashier_parent.setCenter(Model.getInstance().getViewManager().getSearchByPartialNameView());
                case "Session Date" -> cashier_parent.setCenter(Model.getInstance().getViewManager().getDateSelectionView());
                default -> cashier_parent.setCenter(Model.getInstance().getViewManager().getCashierWelcomeView());
            }
        });
    }


}
