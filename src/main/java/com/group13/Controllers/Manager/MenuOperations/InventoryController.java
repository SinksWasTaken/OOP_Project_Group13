package com.group13.Controllers.Manager.MenuOperations;

import com.group13.Models.ConnectionModel;
import com.group13.Models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class InventoryController {

    @FXML
    private TableView<Product> inventoryTable;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, Integer> priceColumn;

    @FXML
    private TableColumn<Product, String> imagePathColumn;

    @FXML
    private TableColumn<Product, Integer> stockCountColumn;

    @FXML
    public void initialize() {
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        imagePathColumn.setCellValueFactory(new PropertyValueFactory<>("productImagePath"));
        stockCountColumn.setCellValueFactory(new PropertyValueFactory<>("stockCount"));
        loadInventoryList();
    }

    private void loadInventoryList() {
        ObservableList<Product> inventories = FXCollections.observableArrayList();
        String query = "SELECT * FROM products ORDER BY productName ASC";

        try (Connection connection = ConnectionModel.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                inventories.add(new Product(resultSet.getString("productName"), resultSet.getInt("price"), resultSet.getString("productImagePath"), resultSet.getInt("stockCount")));
            }

            inventoryTable.setItems(inventories);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}