package com.group13.Controllers;

import com.group13.Models.Model;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class LogoutController {

    @FXML
    public void handleLogout() {

        Stage stage = (Stage) Model.getInstance().getViewManager().getCurrentStage();
        if (stage != null) {
            stage.close();
        }

        Model.getInstance().setCurrentWorker(null);

        Model.getInstance().getViewManager().showLoginWindow(null);
    }
}