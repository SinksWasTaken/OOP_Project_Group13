package com.group13.Controllers.Cashier.MenuOperations;

import com.group13.Models.Model;
import com.group13.Models.MovieModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SecondStageController {

    @FXML
    private Button cancelButton;

    @FXML
    private Button nextStageButton;

    @FXML
    public void initialize() {
        cancelButton.setOnAction(e -> cancelSecondStage());
    }

    private void cancelSecondStage() {
        Model.getInstance().setMovieModel(null);
        Model.getInstance().getViewManager().getCashierSelectedMenuItem().set("Search By Genre");
    }
}
