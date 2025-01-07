package com.group13.Controllers.Admin;

import com.group13.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {

    public BorderPane admin_parent;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewManager().getAdminSelectedMenuItem().addListener((observable, oldVal, newVal) -> {
            switch (newVal) {
                case "Home" -> admin_parent.setCenter(Model.getInstance().getViewManager().getAdminWelcome());
                case "Add New Movie" -> admin_parent.setCenter(Model.getInstance().getViewManager().getAddNewMovie());
                case "Update Movie" -> admin_parent.setCenter(Model.getInstance().getViewManager().getUpdateMovie());
                case "Create Schedule" -> admin_parent.setCenter(Model.getInstance().getViewManager().getCreateSchedule());
                case "Update Schedule" -> admin_parent.setCenter(Model.getInstance().getViewManager().getUpdateSchedule());
                case "Customer Request" -> admin_parent.setCenter(Model.getInstance().getViewManager().getCustomerRequests());
                default -> admin_parent.setCenter(Model.getInstance().getViewManager().getAdminWelcome());
            }
        });
    }
}
