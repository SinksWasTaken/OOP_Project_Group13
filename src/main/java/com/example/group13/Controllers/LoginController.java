package com.example.group13.Controllers;

import com.example.group13.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Button admin_btn;
    public Button manager_btn;
    public Button cashier_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        admin_btn.setOnAction(event -> Model.getInstance().getViewManager().showAdminWindow());
        manager_btn.setOnAction(event -> Model.getInstance().getViewManager().showManagerWindow());
        cashier_btn.setOnAction(event -> Model.getInstance().getViewManager().showCashierWindow());
    }
}
