package com.group13.Views;

import com.group13.Controllers.Admin.AdminController;
import com.group13.Controllers.Cashier.CashierController;
import com.group13.Controllers.Manager.ManagerController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewManager {

    // Admin Views
    private final StringProperty adminSelectedMenuItem;
    private AnchorPane adminOperation1;
    private AnchorPane adminOperation2;

    // Manager Views
    private AnchorPane managerView;

    // Cashier Views
    private AnchorPane cashierView;

    public ViewManager() {
        adminSelectedMenuItem = new SimpleStringProperty("");
    }

    public StringProperty getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }

    public AnchorPane getAdminOperation1() {
        if(adminOperation1 == null) {
            try {
                adminOperation1 = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin-operation1-view.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return adminOperation1;
    }

    public AnchorPane getAdminOperation2() {
        if(adminOperation2 == null) {
            try {
                adminOperation2 = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin-operation2-view.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return adminOperation2;
    }

    // Login Views

    public void showLoginWindow()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login/login-view.fxml"));
        createStage(loader, "Login");
    }

    public void showAdminWindow()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin-view.fxml"));
        AdminController adminController = new AdminController();
        loader.setController(adminController);
        createStage(loader, "Admin");
    }

    public void showManagerWindow()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Manager/manager-view.fxml"));
        ManagerController managerController = new ManagerController();
        loader.setController(managerController);
        createStage(loader, "Manager");
    }

    public void showCashierWindow()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Cashier/cashier-view.fxml"));
        CashierController cashierController = new CashierController();
        loader.setController(cashierController);
        createStage(loader, "Cashier");

    }

    private void createStage(FXMLLoader loader, String stageName)
    {
        Scene scene = null;

        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setScene(scene);

        if(stageName != null) {
            switch (stageName) {
                case "Admin" -> stage.setTitle("Cinema Center Application | Admin");
                case "Manager" -> stage.setTitle("Cinema Center Application | Manager");
                case "Cashier" -> stage.setTitle("Cinema Center Application | Cashier");
                case "Login" -> stage.setTitle("Cinema Center Application | Login");
            }
        }
        stage.show();
    }
}
