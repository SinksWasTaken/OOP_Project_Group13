package com.group13.Controllers.Manager.MenuOperations;

import com.group13.Models.ConnectionModel;
import com.group13.Models.Model;
import com.group13.Models.Worker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.Optional;

public class WorkerController {
    @FXML
    private TableView<Worker> workerTable;

    @FXML
    private TableColumn<Worker, String> usernameColumn;

    @FXML
    private TableColumn<Worker, String> firstnameColumn;

    @FXML
    private TableColumn<Worker, String> lastnameColumn;

    @FXML
    private TableColumn<Worker, String> roleColumn;

    @FXML
    private Button addWorkerButton;

    @FXML
    private Button updateWorkerButton;

    @FXML
    private Button deleteWorkerButton;

    @FXML
    public void initialize() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        workerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        loadWorkerList();

        deleteWorkerButton.setDisable(true);
        updateWorkerButton.setDisable(true);

        workerTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            boolean isItemSelected = newValue != null;
            deleteWorkerButton.setDisable(!isItemSelected);
            updateWorkerButton.setDisable(!isItemSelected);
            Model.getInstance().setNextWorker(newValue);
        });

        addWorkerButton.setOnAction(event -> Model.getInstance().getViewManager().getManagerSelectedMenuItem().set("Add Worker"));
        updateWorkerButton.setOnAction(event -> Model.getInstance().getViewManager().getManagerSelectedMenuItem().set("Update Worker"));
        deleteWorkerButton.setOnAction(event -> deleteSelectedWorker());
    }

    private void loadWorkerList() {
        ObservableList<Worker> workers = FXCollections.observableArrayList();
        String query = "SELECT w.username, w.firstname, w.lastname, r.role_name FROM workers w INNER JOIN roles r ON w.role = r.role_id ORDER BY r.role_name ASC";

        try (Connection connection = ConnectionModel.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                workers.add(new Worker(
                        resultSet.getString("username"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("role_name")
                ));
            }

            workerTable.setItems(workers);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteSelectedWorker() {

        Worker selectedWorker = workerTable.getSelectionModel().getSelectedItem();
        Worker currentWorker = Model.getInstance().getCurrentWorker();

        if (selectedWorker != null) {
            String nextWorkerUsername = selectedWorker.getUsername();
            String currentWorkerUsername = currentWorker.getUsername();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Delete Worker");
            alert.setContentText("Are you sure you want to delete the selected worker?");

            Optional<ButtonType> result = alert.showAndWait();

            if(Objects.equals(currentWorkerUsername, nextWorkerUsername)) {
                showErrorAlert("You cannot delete yourself!");
            }

            else
            {
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    String deleteQuery = "DELETE FROM workers WHERE username = ?";

                    try (Connection connection = ConnectionModel.getConnection();
                         PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

                        preparedStatement.setString(1, nextWorkerUsername);
                        preparedStatement.executeUpdate();

                        loadWorkerList();

                    } catch (Exception e) {
                        e.printStackTrace();
                        showErrorAlert("An error occurred while deleting the product.");
                    }
                }
            }
        } else {
            showWarningAlert("No Selection", "Please select a product to delete.");
        }
    }

    private void showWarningAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showErrorAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An Error Occurred");
        alert.setContentText(content);
        alert.showAndWait();
    }


}
