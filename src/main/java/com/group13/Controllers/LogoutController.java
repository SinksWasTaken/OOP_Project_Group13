package com.group13.Controllers;

import com.group13.Models.Model;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.File;

public class LogoutController {

    @FXML
    public void handleLogout() {

        Stage stage = (Stage) Model.getInstance().getViewManager().getCurrentStage();
        if (stage != null) {
            stage.close();
        }

        Model.getInstance().setCurrentWorker(null);

        clearTempFolder();

        Model.getInstance().getViewManager().showLoginWindow(null);
    }

    private void clearTempFolder() {
        File tempFolder = new File("src/main/resources/Images/temp");
        if (tempFolder.exists() && tempFolder.isDirectory()) {
            File[] files = tempFolder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!file.delete()) {
                        System.out.println("Failed to delete temporary file: " + file.getAbsolutePath());
                    }
                }
            }
        }
    }
}