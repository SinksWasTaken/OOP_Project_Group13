package com.group13.Views;

import com.group13.Controllers.Admin.AdminDashboardController;
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

    private AnchorPane adminWelcome;
    private AnchorPane addNewMovie;
    private AnchorPane updateMovie;
    private AnchorPane createSchedule;
    private AnchorPane updateSchedule;
    private AnchorPane customerRequest;

    // Manager Views
    private AnchorPane managerView;

    // Cashier Views
    private AnchorPane cashierView;

    // Current Stage
    private Stage currentStage;

    public ViewManager() {
        adminSelectedMenuItem = new SimpleStringProperty("");
    }

    public StringProperty getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }

    public AnchorPane getAdminWelcome() {
        if(adminWelcome == null) {
            try {
                adminWelcome = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin-welcome-view.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return adminWelcome;
    }

    public AnchorPane getAddNewMovie() {
        if(addNewMovie == null) {
            try {
                addNewMovie = new FXMLLoader(getClass().getResource("/Fxml/Admin/Menu/add-new-movie-view.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return addNewMovie;
    }

    public AnchorPane getUpdateMovie() {
        try {
            updateMovie = new FXMLLoader(getClass().getResource("/Fxml/Admin/Menu/update-movie-view.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updateMovie;
    }


    public AnchorPane getCreateSchedule() {
        if(createSchedule == null) {
            try {
                createSchedule = new FXMLLoader(getClass().getResource("/Fxml/Admin/Menu/create-schedule-view.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return createSchedule;
    }

    public AnchorPane getUpdateSchedule() {
        if(updateSchedule == null) {
            try {
                updateSchedule = new FXMLLoader(getClass().getResource("/Fxml/Admin/Menu/update-schedule-view.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return updateSchedule;
    }

    public AnchorPane getCustomerRequests() {
        if(customerRequest == null) {
            try {
                customerRequest = new FXMLLoader(getClass().getResource("/Fxml/Admin/Menu/customer-request-view.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return customerRequest;
    }

    // Login Views

    public void showLoginWindow(Stage currentStage)
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login/login-view.fxml"));
        createStage(loader, "Login", currentStage);
    }

    public void showAdminWindow(Stage currentStage)
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin-view.fxml"));
        AdminDashboardController adminController = new AdminDashboardController();
        loader.setController(adminController);
        createStage(loader, "Admin", currentStage);
    }

    public void showManagerWindow(Stage currentStage)
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Manager/manager-view.fxml"));
        ManagerController managerController = new ManagerController();
        loader.setController(managerController);
        createStage(loader, "Manager", currentStage);
    }

    public void showCashierWindow(Stage currentStage)
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Cashier/cashier-view.fxml"));
        CashierController cashierController = new CashierController();
        loader.setController(cashierController);
        createStage(loader, "Cashier", currentStage);
    }

    private void createStage(FXMLLoader loader, String stageName, Stage currentStage)
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

        if (currentStage != null) {
            currentStage.close();
        }

        this.currentStage = stage;
    }

    public Stage getCurrentStage() {
        return currentStage;
    }
}