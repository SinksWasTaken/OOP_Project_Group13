package com.group13.Controllers.Admin;

import com.group13.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    public BorderPane admin_parent;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewManager().getAdminSelectedMenuItem().addListener((observable, oldVal, newVal) -> {
            switch (newVal) {
                case "Operation1" -> admin_parent.setCenter(Model.getInstance().getViewManager().getAdminOperation1());
                case "Operation2" -> admin_parent.setCenter(Model.getInstance().getViewManager().getAdminOperation2());
                default -> admin_parent.setCenter(Model.getInstance().getViewManager().getAdminOperation1());
            }
        });
    }
}
