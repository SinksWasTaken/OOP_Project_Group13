package com.group13.Controllers.Admin;

import com.group13.Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {

    @FXML
    public javafx.scene.control.Button operation1_btn;

    @FXML
    public javafx.scene.control.Button operation2_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        operation1_btn.setOnAction(event -> onOperation1());
        operation2_btn.setOnAction(event -> onOperation2());
    }

    private void onOperation1() {
        Model.getInstance().getViewManager().getAdminSelectedMenuItem().set("Operation1");
    }

    private void onOperation2() {
        Model.getInstance().getViewManager().getAdminSelectedMenuItem().set("Operation2");
    }
}
