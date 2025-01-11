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
        String query = "SELECT totalRevenue FROM tickets";
        double totalRevenue = 0.0;

        try (Connection connection = ConnectionModel.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                totalRevenue += resultSet.getDouble("totalRevenue");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalRevenue;
    }

    private double calculateTotalTaxAmounts() {
        String query = "SELECT totalTax FROM tickets";
        double totalTax = 0.0;

        try (Connection connection = ConnectionModel.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                totalTax += resultSet.getDouble("totalTax");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalTax;
    }


}
