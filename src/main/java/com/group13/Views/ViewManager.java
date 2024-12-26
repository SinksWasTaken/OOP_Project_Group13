package com.group13.Views;

import com.group13.Controllers.Admin.AdminController;
import com.group13.Controllers.Cashier.CashierController;
import com.group13.Controllers.Manager.ManagerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewManager {

    public ViewManager() {}

    // Admin Views
    private AnchorPane adminDashboardView;

    public AnchorPane getAdminView() {
        if(adminDashboardView == null) {
            try {
                adminDashboardView = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin-view.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return adminDashboardView;
    }

    // Manager Views
    private AnchorPane managerView;

    // Cashier Views
    private AnchorPane cashierView;

    // Login Views

    public void showLoginWindow()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login/login-view.fxml"));
        Scene scene = null;

        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Cinema Center Application | Login");
        stage.show();
    }

    public void showAdminWindow()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin-view.fxml"));
        AdminController adminController = new AdminController();
        loader.setController(adminController);

        Scene scene = null;

        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Cinema Center Application | Admin");
        stage.show();
    }

    public void showManagerWindow()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Manager/manager-view.fxml"));
        ManagerController managerController = new ManagerController();
        loader.setController(managerController);

        Scene scene = null;

        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Cinema Center Application | Manager");
        stage.show();
    }

    public void showCashierWindow()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Cashier/cashier-view.fxml"));
        CashierController cashierController = new CashierController();
        loader.setController(cashierController);
        Scene scene = null;

        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Cinema Center Application | Cashier");
        stage.show();
    }
}
