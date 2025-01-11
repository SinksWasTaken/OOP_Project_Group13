package com.group13.Controllers.Manager.MenuOperations;

import com.group13.Models.ConnectionModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class AddProductController {

    @FXML
    private TextField productNameField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField stockField;

    @FXML
    private Button addProductButton;

    @FXML
    private Button uploadImageButton;

    @FXML
    private ImageView productImageView;

    private File tempImageFile;

    @FXML
    public void initialize() {
        Image defaultImage = new Image(getClass().getResource("/Images/movies/default-image.jpg").toString());
        productImageView.setImage(defaultImage);

        addProductButton.setOnAction(event -> {
            if (validateInputs()) {
                String productName = productNameField.getText();
                String priceStr = priceField.getText();
                double price = Double.parseDouble(priceStr);
                String stockStr = stockField.getText();
                int stock = Integer.parseInt(stockStr);
                String productImagePath = saveImageToTargetDirectory();
                addProduct(productName, price, productImagePath, stock);
            }
        });

        uploadImageButton.setOnAction(event -> uploadImage());
    }

    @FXML
    public void uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.webp"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                tempImageFile = new File("src/main/resources/Images/temp/" + selectedFile.getName());
                Files.copy(selectedFile.toPath(), tempImageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                Image image = new Image(tempImageFile.toURI().toString());
                productImageView.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String saveImageToTargetDirectory() {
        if (tempImageFile != null) {
            File targetFile = new File("src/main/resources/Images/products/" + tempImageFile.getName());
            try {
                Files.move(tempImageFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                return "src/main/resources/Images/products/" + targetFile.getName();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private boolean validateInputs() {
        String productName = productNameField.getText();
        String price = priceField.getText();
        String stock = stockField.getText();

        if (productName.isEmpty() || productName.length() > 50) {
            showAlert("Product Name must be between 1 and 50 characters.");
            return false;
        }

        if (price.isEmpty()) {
            showAlert("Price cannot be empty.");
            return false;
        }

        if (stock.isEmpty()) {
            showAlert("Stock cannot be empty.");
            return false;
        }

        if (tempImageFile == null || !tempImageFile.exists()) {
            showAlert("Please upload a product image.");
            return false;
        }

        if (isProductNameDuplicate(productName)) {
            showAlert("A product name with the same name already exists. Please choose a different name.");
            return false;
        }

        return true;
    }

    private boolean isProductNameDuplicate(String productName) {
        String query = "SELECT COUNT(*) FROM products WHERE productName = ?";
        try (var connection = ConnectionModel.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, productName);

            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void addProduct(String productName, double price, String productImagePath, int stockCount) {
        String query = "INSERT INTO products (productName, price, productImagePath, stockCount) VALUES (?, ?, ?, ?)";

        try (var connection = ConnectionModel.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, productName);
            preparedStatement.setDouble(2, price);
            preparedStatement.setString(3, productImagePath);
            preparedStatement.setInt(4, stockCount);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showSuccessAlert("Product added successfully!");
                resetForm();
            } else {
                showAlert("Failed to add the product.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetForm() {
        productNameField.clear();
        priceField.clear();
        stockField.clear();
        productImageView.setImage(new Image(getClass().getResource("/Images/movies/default-image.jpg").toString()));
        tempImageFile = null;
    }
}
