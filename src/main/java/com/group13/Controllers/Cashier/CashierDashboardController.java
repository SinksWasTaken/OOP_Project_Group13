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
                case "Search By Genre" -> cashier_parent.setCenter(Model.getInstance().getViewManager().getSearchByGenreView());
                case "Search By Name" -> cashier_parent.setCenter(Model.getInstance().getViewManager().getSearchByNameView());
                case "Search By Partial Name" -> cashier_parent.setCenter(Model.getInstance().getViewManager().getSearchByPartialNameView());
<<<<<<< HEAD
                case "Session Date" -> cashier_parent.setCenter(Model.getInstance().getViewManager().getDateSelectionView());
                default -> cashier_parent.setCenter(Model.getInstance().getViewManager().getCashierWelcomeView());
=======
                case "Second Stage" -> cashier_parent.setCenter(Model.getInstance().getViewManager().getSecondStageView());
                case "Hall A" -> cashier_parent.setCenter(Model.getInstance().getViewManager().getHallAView());
                default -> cashier_parent.setCenter(Model.getInstance().getViewManager().getSearchByGenreView());
>>>>>>> 36d79866b1a2e0be4181c8a11752cf36b9731cc4
            }
        });
    }
}
