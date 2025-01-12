package com.group13.Controllers.Cashier.SelectOperations;

import com.group13.Models.ConnectionModel;
import com.group13.Models.Model;
import com.group13.Models.Product;
import com.group13.Models.TicketModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CashierProductSelectionController {

    @FXML
    private GridPane productGrid;

    @FXML
    private Button cancelButton;

    @FXML
    private Button nextStageButton;

    private final List<Product> products = new ArrayList<>();

    @FXML
    public void initialize() {
        loadProductGrid();

        cancelButton.setOnAction(e -> cancelSelection());
        nextStageButton.setOnAction(e -> nextStage());
    }

    private void loadProductGrid() {
        ObservableList<Product> productList = FXCollections.observableArrayList();
        String query = "SELECT * FROM products ORDER BY productName ASC";

        try (Connection connection = ConnectionModel.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("productID"),
                        resultSet.getString("productName"),
                        resultSet.getDouble("price"),
                        resultSet.getString("productImagePath"),
                        resultSet.getInt("stockCount")
                );
                productList.add(product);
                products.add(product);
            }

            int row = 0;
            int col = 0;
            for (Product product : productList) {
                VBox productBox = createProductBox(product);
                productGrid.add(productBox, col, row);
                col++;
                if (col == 4) {
                    col = 0;
                    row++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private VBox createProductBox(Product product) {
        VBox vbox = new VBox();
        vbox.setSpacing(5);

        Button productButton = new Button();
        productButton.setGraphic(product.getProductImageView());
        productButton.setText(product.getProductName() + "\nPrice: $" + product.getPrice() + "\nStock: " + product.getStockCount());
        productButton.getStyleClass().add("product-button");

        Spinner<Integer> quantitySpinner = new Spinner<>();
        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, product.getStockCount(), 0));
        quantitySpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue > product.getStockCount()) {
                quantitySpinner.getValueFactory().setValue(product.getStockCount());
            } else if (newValue < 0) {
                quantitySpinner.getValueFactory().setValue(0);
            }
        });

        vbox.getChildren().addAll(productButton, quantitySpinner);
        return vbox;
    }

    private void cancelSelection() {
        Model.getInstance().getViewManager().getCashierSelectedMenuItem().set("Previous Stage");
    }

    private void nextStage() {
        List<String> productIDs = new ArrayList<>();
        for (VBox productBox : productGrid.getChildren().stream().map(node -> (VBox) node).collect(Collectors.toList())) {
            Spinner<Integer> quantitySpinner = (Spinner<Integer>) productBox.getChildren().get(1);
            int quantity = quantitySpinner.getValue();
            if (quantity > 0) {
                Button productButton = (Button) productBox.getChildren().get(0);
                Product product = products.stream()
                        .filter(p -> p.getProductName().equals(productButton.getText().split("\n")[0]))
                        .findFirst()
                        .orElse(null);
                if (product != null) {
                    for (int i = 0; i < quantity; i++) {
                        productIDs.add(String.valueOf(product.getProductID()));
                    }
                    product.setStockCount(product.getStockCount() - quantity);
                }
            }
        }

        TicketModel ticketModel = Model.getInstance().getTicketModel();
        ticketModel.setProductIDs(productIDs);

        productGrid.getChildren().clear();
        loadProductGrid();

        Model.getInstance().getViewManager().getCashierSelectedMenuItem().set("Next Stage");
    }
}