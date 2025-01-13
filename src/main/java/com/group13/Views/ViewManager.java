package com.group13.Views;

import com.group13.Controllers.Admin.AdminDashboardController;
import com.group13.Controllers.Cashier.CashierDashboardController;
import com.group13.Controllers.Manager.ManagerDashboardController;

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

    public StringProperty getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }

    public AnchorPane getAdminWelcomeView() {
        return loadView("/Fxml/Admin/admin-welcome-view.fxml");
    }

    public AnchorPane getAddNewMovieView() {
        return loadView("/Fxml/Admin/Menu/add-new-movie-view.fxml");
    }

    public AnchorPane getUpdateMovieView() {
        return loadView("/Fxml/Admin/Menu/update-movie-view.fxml");
    }

    public AnchorPane getCreateScheduleView() {
        return loadView("/Fxml/Admin/Menu/create-schedule-view.fxml");
    }

    public AnchorPane getUpdateScheduleView() {
        return loadView("/Fxml/Admin/Menu/update-schedule-view.fxml");
    }

    public AnchorPane getCustomerRequestsView() {
        return loadView("/Fxml/Admin/Menu/customer-request-view.fxml");
    }

    // Manager Views

    private final StringProperty managerSelectedMenuItem;

    public StringProperty getManagerSelectedMenuItem() {
        return managerSelectedMenuItem;
    }

    public AnchorPane getInventoryView() {
        return loadView("/Fxml/Manager/Menu/inventory-view.fxml");
    }

    public AnchorPane getAddProductView() {
        return loadView("/Fxml/Manager/Menu/add-product-view.fxml");
    }

    public AnchorPane getUpdateProductView() {
        return loadView("/Fxml/Manager/Menu/update-product-view.fxml");
    }

    public AnchorPane getPriceView() {
        return loadView("/Fxml/Manager/Menu/price-view.fxml");
    }

    public AnchorPane getRevenueTaxView() {
        return loadView("/Fxml/Manager/Menu/revenue-tax-view.fxml");
    }

    public AnchorPane getWorkerView() {
        return loadView("/Fxml/Manager/Menu/worker-view.fxml");
    }

    public AnchorPane getAddWorkerView() {
        return loadView("/Fxml/Manager/Menu/add-worker-view.fxml");
    }

    public AnchorPane getUpdateWorkerView() {
        return loadView("/Fxml/Manager/Menu/update-worker-view.fxml");
    }

    // Cashier Views

    private final StringProperty cashierSelectedMenuItem;

    public StringProperty getCashierSelectedMenuItem() {
        return cashierSelectedMenuItem;
    }

    public AnchorPane getSearchByGenreView() {
        return loadView("/Fxml/Cashier/Menu/search-by-genre-view.fxml");
    }

    public AnchorPane getSearchByNameView() {
        return loadView("/Fxml/Cashier/Menu/search-by-title-view.fxml");
    }

    public AnchorPane getSearchByPartialNameView() {
        return loadView("/Fxml/Cashier/Menu/search-by-partial-title-view.fxml");
    }

    public AnchorPane getSessionSelectionView() {
        return loadView("/Fxml/Cashier/Menu/session-selection-view.fxml");
    }

    public AnchorPane getHallView()
    {
        return loadView("/Fxml/Cashier/Menu/hall-view.fxml");
    }

    public AnchorPane getProductSelectionView()
    {
        return loadView("/Fxml/Cashier/Menu/product-selection-view.fxml");
    }

    public AnchorPane getPurchaseView()
    {
        return loadView("/Fxml/Cashier/Menu/purchase-view.fxml");
    }

    private AnchorPane loadView(String fxmlPath) {
        try {
            return new FXMLLoader(getClass().getResource(fxmlPath)).load();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}