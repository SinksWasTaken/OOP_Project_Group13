package com.group13.Controllers.Manager.MenuOperations;

import com.group13.Models.ConnectionModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class RevenueTaxController {
    @FXML
    private Label revenueLabel;

    @FXML
    private Label taxAmountLabel;

    @FXML
    public void initialize() {
        revenueLabel.setText(String.valueOf(calculateTotalRevenue()));
        taxAmountLabel.setText(String.valueOf(calculateTotalTaxAmounts()));
    }


    private double calculateTotalRevenue() {

        double totalTicketPrice = calculateTotalTicket();
        double totalProductPrice = calculateTotalProduct();

        double totalRevenue = 0.0;

        totalRevenue = totalTicketPrice + totalProductPrice;

        return totalRevenue;
    }

    private double calculateTotalTicket() {
        String query = "SELECT totalTicketPrice FROM tickets";

        double totalTicketPrice = 0;

        try (Connection connection = ConnectionModel.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                totalTicketPrice += resultSet.getDouble("totalTicketPrice");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalTicketPrice;
    }

    private double calculateTotalProduct() {
        String query = "SELECT totalProductPrice FROM tickets";

        double totalProductPrice = 0;

        try (Connection connection = ConnectionModel.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                totalProductPrice += resultSet.getDouble("totalProductPrice");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalProductPrice;
    }

    private double calculateTotalTaxAmounts() {
        String query = "SELECT * FROM pricetaxdiscount";

        double ticketTax = 0.0;
        double productTax = 0.0;

        double totalTicketPrice = calculateTotalTicket();
        double totalProductPrice = calculateTotalProduct();

        double totalTax = 0.0;

        try (Connection connection = ConnectionModel.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                ticketTax = resultSet.getDouble("ticketTaxRate");
                productTax = resultSet.getDouble("productTaxRate");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        ticketTax = (ticketTax / 100);
        productTax = (productTax / 100);

        totalTicketPrice *= ticketTax;
        totalProductPrice *= productTax;

        totalTax = totalTicketPrice + totalProductPrice;

        return totalTax;
    }


}
