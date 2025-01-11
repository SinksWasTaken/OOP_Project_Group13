package com.group13.Controllers.Cashier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.group13.DatabaseConnection;

public class CashierProductHandlerController {

    // method to retrieve product details
    public void getProductDetails(String productName) {
        try {
            Connection connection = DatabaseConnection.connect();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String query = "SELECT * FROM products WHERE productName = '" + productName + "'";
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                String name = rs.getString("productName");
                double price = rs.getDouble("price");
                String imagePath = rs.getString("productImagePath");
                int stockCount = rs.getInt("stockCount");

                // retrieve data (name, price, imagePath, stockCount)
                System.out.println("Product Name: " + name);
                System.out.println("Price: " + price);
                System.out.println("Image Path: " + imagePath);
                System.out.println("Stock Count: " + stockCount);
            } else {
                System.out.println("Product not found.");
            }

            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // method to update the product stock
    public void updateProductStock(String productName, int quantity) {
        try {
            Connection connection = DatabaseConnection.connect();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String query = "SELECT * FROM products WHERE productName = '" + productName + "'";
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                int currentStock = rs.getInt("stockCount");

                if (currentStock >= quantity) {
                    rs.updateInt("stockCount", currentStock - quantity);
                    rs.updateRow();
                    System.out.println("Stock updated for product: " + productName);
                } else {
                    System.out.println("Not enough stock for product: " + productName);
                }
            }

            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // method to update extra products in tickets table
    public void updateExtrasForTicket(int ticketID, String productName, double productPrice) {
        try {
            Connection connection = DatabaseConnection.connect();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            // Fetch the product's price from the products table
            String productQuery = "SELECT price FROM products WHERE productName = '" + productName + "'";
            ResultSet productResultSet = statement.executeQuery(productQuery);

            if (productResultSet.next()) {
                // Retrieve the ticket data from the tickets table
                String ticketQuery = "SELECT * FROM tickets WHERE ticketID = " + ticketID;
                ResultSet ticketResultSet = statement.executeQuery(ticketQuery);

                if (ticketResultSet.next()) {
                    String currentExtras = ticketResultSet.getString("extrasProducts");
                    currentExtras = (currentExtras == null ? "" : currentExtras) + productName + "; ";

                    double currentPrice = ticketResultSet.getDouble("price");

                    // Update the ticket with the new extras and price
                    ticketResultSet.updateString("extrasProducts", currentExtras);
                    ticketResultSet.updateDouble("price", currentPrice + productPrice);
                    ticketResultSet.updateRow();

                    System.out.println("Extras updated for ticket ID: " + ticketID);
                }
                ticketResultSet.close();
            }

            productResultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void handleProductSelection(String productName, int ticketID) {
        // Retrieve product details and price
        double productPrice = getProductPrice(productName);

        if (productPrice != -1) {  // Ensure the product exists and price is valid
            // Verify availability, then update stock and ticket extras
            updateProductStock(productName, 1);
            updateExtrasForTicket(ticketID, productName, productPrice); // Use the actual product price

            System.out.println("Product: " + productName + " was added to ticket ID: " + ticketID);
        } else {
            System.out.println("Product: " + productName + " not found or invalid price.");
        }
    }

    // Method to retrieve product price from the database
    private double getProductPrice(String productName) {
        double productPrice = -1; // Default to -1 if product is not found or an error occurs
        try {
            Connection connection = DatabaseConnection.connect();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String query = "SELECT price FROM products WHERE productName = '" + productName + "'";
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                productPrice = rs.getDouble("price");
            }

            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productPrice;
    }
}
