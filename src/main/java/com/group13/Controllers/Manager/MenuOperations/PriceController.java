package com.group13.Controllers.Manager.MenuOperations;

import com.group13.Models.ConnectionModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class PriceController {
    @FXML
    private TextField ticketPriceField;

    @FXML
    private TextField discountField;

    @FXML
    private TextField ticketTaxField;

    @FXML
    private TextField productTaxField;

    @FXML
    private Button updatePricesButton;

    @FXML
    public void initialize() {
        loadPrices();

        updatePricesButton.setOnAction(event -> updatePrices());
    }

    public void loadPrices() {
        String query = "SELECT movieTicketPrice, discountRate, ticketTaxRate, productTaxRate FROM pricetaxdiscount WHERE taxID = 1";

        try (Connection connection = ConnectionModel.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                ticketPriceField.setText(String.valueOf(resultSet.getDouble("movieTicketPrice")));
                discountField.setText(String.valueOf(resultSet.getDouble("discountRate")));
                ticketTaxField.setText(String.valueOf(resultSet.getDouble("ticketTaxRate")));
                productTaxField.setText(String.valueOf(resultSet.getDouble("productTaxRate")));
            } else {
                System.out.println("No data found in the pricetaxdiscount table for taxID = 1.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePrices() {
        String query = "UPDATE pricetaxdiscount SET movieTicketPrice = ?, discountRate = ?, ticketTaxRate = ?, productTaxRate = ? WHERE taxID = 1";

        try (Connection connection = ConnectionModel.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            double movieTicketPrice = Double.parseDouble(ticketPriceField.getText());
            double discountRate = Double.parseDouble(discountField.getText());
            double ticketTaxRate = Double.parseDouble(ticketTaxField.getText());
            double productTaxRate = Double.parseDouble(productTaxField.getText());

            preparedStatement.setDouble(1, movieTicketPrice);
            preparedStatement.setDouble(2, discountRate);
            preparedStatement.setDouble(3, ticketTaxRate);
            preparedStatement.setDouble(4, productTaxRate);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                showSuccessAlert("Values updated successfully!");
                System.out.println("Prices updated successfully.");
            } else {
                System.out.println("Failed to update prices.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input format. Please enter valid numeric values.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateInputs() {
        String ticketPrice = ticketPriceField.getText();
        String discount = discountField.getText();
        String ticketTax = ticketTaxField.getText();
        String productTax = productTaxField.getText();

        if (ticketPrice.isEmpty()){
            showAlert("Ticket price cannot be empty.");
            return false;
        }

        if (discount.isEmpty()) {
            showAlert("Discount cannot be empty.");
            return false;
        }

        if (ticketTax.isEmpty()) {
            showAlert("Ticket tax cannot be empty.");
            return false;
        }

        if (productTax.isEmpty()) {
            showAlert("Product tax cannot be empty.");
        }

        return true;
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
