package com.group13.Views;

import com.group13.Controllers.Admin.AdminDashboardController;
import com.group13.Controllers.Cashier.CashierDashboardController;
import com.group13.Controllers.Cashier.CashierWelcomeController;
import com.group13.Controllers.Manager.ManagerDashboardController;
import com.group13.Controllers.Manager.ManagerWelcomeController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewManager {

    public ViewManager() {
        adminSelectedMenuItem = new SimpleStringProperty("");
        managerSelectedMenuItem = new SimpleStringProperty("");
        cashierSelectedMenuItem = new SimpleStringProperty("");
    }

    // Stage

    private Stage currentStage;

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
        ManagerDashboardController managerController = new ManagerDashboardController();
        loader.setController(managerController);
        createStage(loader, "Manager", currentStage);
    }

    public void showCashierWindow(Stage currentStage)
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Cashier/cashier-view.fxml"));
        CashierDashboardController cashierController = new CashierDashboardController();
        loader.setController(cashierController);
        createStage(loader, "Cashier", currentStage);
    }

    // Admin Views

    private final StringProperty adminSelectedMenuItem;
    private AnchorPane adminWelcome;
    private AnchorPane addNewMovie;
    private AnchorPane updateMovie;
    private AnchorPane createSchedule;
    private AnchorPane updateSchedule;
    private AnchorPane customerRequest;

    public StringProperty getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }


    public AnchorPane getAdminWelcomeView() {
        if(adminWelcome == null) {
            try {
                adminWelcome = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin-welcome-view.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return adminWelcome;
    }

    public AnchorPane getAddNewMovieView() {
        if(addNewMovie == null) {
            try {
                addNewMovie = new FXMLLoader(getClass().getResource("/Fxml/Admin/Menu/add-new-movie-view.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return addNewMovie;
    }

    public AnchorPane getUpdateMovieView() {
        if(updateMovie == null) {
            try {
                updateMovie = new FXMLLoader(getClass().getResource("/Fxml/Admin/Menu/update-movie-view.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return updateMovie;
    }

    public AnchorPane getCreateScheduleView() {
        if(createSchedule == null) {
            try {
                createSchedule = new FXMLLoader(getClass().getResource("/Fxml/Admin/Menu/create-schedule-view.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return createSchedule;
    }

    public AnchorPane getUpdateScheduleView() {
        if(updateSchedule == null) {
            try {
                updateSchedule = new FXMLLoader(getClass().getResource("/Fxml/Admin/Menu/update-schedule-view.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return updateSchedule;
    }

    public AnchorPane getCustomerRequestsView() {
        if(customerRequest == null) {
            try {
                customerRequest = new FXMLLoader(getClass().getResource("/Fxml/Admin/Menu/customer-request-view.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return customerRequest;
    }

    // Manager Views

    private final StringProperty managerSelectedMenuItem;
    private AnchorPane managerWelcome;
    private AnchorPane inventory;
    private AnchorPane price;
    private AnchorPane revenueTax;
    private AnchorPane worker;

    public StringProperty getManagerSelectedMenuItem() {
        return managerSelectedMenuItem;
    }

    public AnchorPane getManagerWelcomeView() {
        if(managerWelcome == null) {
            try {
                managerWelcome = new FXMLLoader(getClass().getResource("/Fxml/Manager/manager-welcome-view.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return managerWelcome;
    }

    public AnchorPane getInventoryView() {
        if(inventory == null) {
            try {
                inventory = new FXMLLoader(getClass().getResource("/Fxml/Manager/Menu/inventory-view.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return inventory;
    }

    public AnchorPane getPriceView() {
        if(price == null) {
            try {
                price = new FXMLLoader(getClass().getResource("/Fxml/Manager/Menu/price-view.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return price;
    }

    public AnchorPane getRevenueTaxView() {
        if(revenueTax == null) {
            try {
                revenueTax = new FXMLLoader(getClass().getResource("/Fxml/Manager/Menu/revenue-tax-view.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return revenueTax;
    }

    public AnchorPane getWorkerView() {
        if(worker == null) {
            try {
                worker = new FXMLLoader(getClass().getResource("/Fxml/Manager/Menu/worker-view.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return worker;
    }

    // Cashier Views

    private final StringProperty cashierSelectedMenuItem;
    private AnchorPane cashierView;

    public StringProperty getCashierSelectedMenuItem() {
        return cashierSelectedMenuItem;
    }
}