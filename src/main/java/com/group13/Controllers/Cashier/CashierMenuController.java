package com.group13.Controllers.Cashier;

import com.group13.Controllers.LogoutController;
import com.group13.Models.Model;
import com.group13.Models.TicketModel;
import com.group13.Models.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CashierMenuController implements Initializable {

    @FXML
    public Label username_manager;

    @FXML
    public Label role_manager;

    @FXML
    public javafx.scene.control.Button home_btn;

    @FXML
    public javafx.scene.control.Button searchByGenre_btn;

    @FXML
    public javafx.scene.control.Button searchByName_btn;

    @FXML
    public javafx.scene.control.Button searchByPartialName_btn;

    @FXML
    public javafx.scene.control.Button selectDate_btn;

    @FXML
    public javafx.scene.control.Button logout_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Worker currentWorker = Model.getInstance().getCurrentWorker();
        if (currentWorker != null) {
            username_manager.setText("Username: " + currentWorker.getUsername());
            role_manager.setText("Role: " + currentWorker.getRole());
        }

        addListeners();
    }

    private void addListeners() {
        home_btn.setOnAction(event -> onHome());
        searchByGenre_btn.setOnAction(event -> onSearchByGenre());
        searchByName_btn.setOnAction(event -> onSearchByName());
        searchByPartialName_btn.setOnAction(event -> onSearchByPartialName());
        selectDate_btn.setOnAction(event -> onDateSelection());
        logout_btn.setOnAction(event -> onLogout());
    }

    private void onHome() {
        Model.getInstance().getViewManager().getCashierSelectedMenuItem().set("Home");
        Model.getInstance().getViewManager().getCashierWelcomeView();
    }

    private void onSearchByGenre() {
        Model.getInstance().getViewManager().getCashierSelectedMenuItem().set("Search By Genre");
        Model.getInstance().getViewManager().getSearchByGenreView();
    }

    private void onSearchByName() {
        Model.getInstance().getViewManager().getCashierSelectedMenuItem().set("Search By Name");
        Model.getInstance().getViewManager().getSearchByNameView();
    }

    private void onSearchByPartialName() {
        Model.getInstance().getViewManager().getCashierSelectedMenuItem().set("Search By Partial Name");
        Model.getInstance().getViewManager().getSearchByPartialNameView();
    }

    private void onDateSelection() {
        TicketModel ticket = new TicketModel();
        ticket.setMovieID(1);
        Model.getInstance().setTicketModel(ticket);

        Model.getInstance().getViewManager().getCashierSelectedMenuItem().set("Session Date");
        Model.getInstance().getViewManager().getDateSelectionView();
    }


    private void onLogout() {
        LogoutController logoutController = new LogoutController();
        logoutController.handleLogout();
    }

}
