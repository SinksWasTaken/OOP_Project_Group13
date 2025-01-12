package com.group13.Controllers.Manager.MenuOperations;

import com.group13.Models.ConnectionModel;
import com.group13.Models.Model;
import com.group13.Models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Optional;

public class InventoryController {

    @FXML
    private TableView<Product> inventoryTable;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, Integer> priceColumn;

    @FXML
    private TableColumn<Product, ImageView> productImageViewColumn;

    @FXML
    private TableColumn<Product, Integer> stockCountColumn;

    @FXML
    private Button addProductButton;

    @FXML
    private Button updateProductButton;

    @FXML
    private Button deleteProductButton;

    @FXML
    public void initialize() {
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockCountColumn.setCellValueFactory(new PropertyValueFactory<>("stockCount"));
        productImageViewColumn.setCellValueFactory(new PropertyValueFactory<>("productImageView"));

        inventoryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        loadInventoryList();

        deleteProductButton.setDisable(true);
        updateProductButton.setDisable(true);

        inventoryTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            boolean isItemSelected = newValue != null;
            deleteProductButton.setDisable(!isItemSelected);
            updateProductButton.setDisable(!isItemSelected);
            Model.getInstance().setSelectedProduct(newValue);
        });

        addProductButton.setOnAction(event -> Model.getInstance().getViewManager().getManagerSelectedMenuItem().set("Add Product"));
        updateProductButton.setOnAction(event -> Model.getInstance().getViewManager().getManagerSelectedMenuItem().set("Update Product"));
        deleteProductButton.setOnAction(event -> deleteSelectedProduct());
    }

    private void loadInventoryList() {
        ObservableList<Product> inventories = FXCollections.observableArrayList();
        String query = "SELECT * FROM products ORDER BY productName ASC";

        try (Connection connection = ConnectionModel.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                inventories.add(new Product(
                        resultSet.getInt("productId"),
                        resultSet.getString("productName"),
                        resultSet.getInt("price"),
                        resultSet.getString("productImagePath"),
                        resultSet.getInt("stockCount")
                ));
            }

            inventoryTable.setItems(inventories);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteSelectedProduct() {
        Product selectedProduct = inventoryTable.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            String productName = selectedProduct.getProductName();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Delete Product");
            alert.setContentText("Are you sure you want to delete the selected product?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                String deleteQuery = "DELETE FROM products WHERE productName = ?";

                try (Connection connection = ConnectionModel.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

                    String imagePath = getProductImagePathFromDatabase(productName);

                    preparedStatement.setString(1, productName);
                    preparedStatement.executeUpdate();

                    if (imagePath != null) {
                        deleteImageFile(imagePath);
                    }

                    loadInventoryList();

                } catch (Exception e) {
                    e.printStackTrace();
                    showErrorAlert("An error occurred while deleting the product.");
                }
            }
        } else {
            showWarningAlert("No Selection", "Please select a product to delete.");
        }
    }

    private String getProductImagePathFromDatabase(String productName) {
        String query = "SELECT productImagePath FROM products WHERE productName = ?";
        try (var connection = ConnectionModel.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, productName);

            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("productImagePath");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("An error occurred while retrieving the product image path.");
        }
        return null;
    }

    private void deleteImageFile(String imagePath) {
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            if (imageFile.delete()) {
                System.out.println("Image file deleted: " + imagePath);
            } else {
                System.out.println("Failed to delete image file: " + imagePath);
                showErrorAlert("Failed to delete image file: " + imagePath);
            }
        } else {
            System.out.println("Image file does not exist: " + imagePath);
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