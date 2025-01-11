package com.group13.Controllers.Manager.MenuOperations;

import com.group13.Models.ConnectionModel;
import com.group13.Models.Model;
import com.group13.Models.Product;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class UpdateProductController {

    @FXML
    private TextField productNameField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField stockField;

    @FXML
    private Button updateProductButton;

    @FXML
    private Button uploadImageButton;

    @FXML
    private ImageView productImageView;

    private File tempImageFile;
    private String currentProductName;

    @FXML
    public void initialize() {

        Product selectedProduct = Model.getInstance().getSelectedProduct();

        if (selectedProduct != null) {
            setProductDetails(
                    selectedProduct.getProductName(),
                    selectedProduct.getPrice(),
                    selectedProduct.getProductImagePath(),
                    selectedProduct.getStockCount()
            );
        } else {
            showErrorAlert("No product selected to update.");
        }

        uploadImageButton.setOnAction(event -> uploadImage());

        updateProductButton.setOnAction(event -> {

            if (validateInputs()) {
                String newProductName = productNameField.getText();
                String priceStr = priceField.getText();
                double price = Double.parseDouble(priceStr);
                String stockStr = stockField.getText();
                int stock = Integer.parseInt(stockStr);
                String newProductImagePath = saveImageToTargetDirectory();

                if (tempImageFile != null) {
                    String oldProductImagePath = getProductImagePathFromDatabase(currentProductName);
                    if (oldProductImagePath != null && !oldProductImagePath.equals(newProductImagePath)) {
                        deleteImageFile(oldProductImagePath);
                    }
                }

                updateProduct(newProductName, price, newProductImagePath, stock);
            }
        });
    }

    public void setProductDetails(String productName, double price, String productImagePath, int stock) {
        currentProductName = productName;
        productNameField.setText(productName);
        priceField.setText(String.valueOf(price));
        stockField.setText(String.valueOf(stock));

        if (productImagePath != null && !productImagePath.isEmpty()) {
            File imageFile = new File(productImagePath);
            if (imageFile.exists()) {
                Image image = new Image(imageFile.toURI().toString());
                productImageView.setImage(image);
            }
        }
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
        return getProductImagePathFromDatabase(currentProductName);
    }

    private boolean validateInputs() {
        String productName = productNameField.getText();
        String price = priceField.getText();
        String stock = stockField.getText();

        if (productName.isEmpty() || productName.length() > 50) {
            showAlert("Product Name must be between 1 and 50 characters.");
            return false;
        }

        if (!productName.equals(currentProductName) && isProductNameDuplicate(productName)) {
            showAlert("Product Name already exists in the database. Please choose a different name.");
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

        if (tempImageFile == null && productImageView.getImage() == null) {
            showAlert("Please upload a product image.");
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

    private void updateProduct(String newProductName, double price, String productImagePath, int stockCount) {
        String query = "UPDATE products SET productName = ?, price = ?, productImagePath = ?, stockCount = ? WHERE productName = ?";

        try (var connection = ConnectionModel.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newProductName);
            preparedStatement.setDouble(2, price);
            preparedStatement.setString(3, productImagePath);
            preparedStatement.setInt(4, stockCount);
            preparedStatement.setString(5, currentProductName);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showSuccessAlert("Product updated successfully!");
            } else {
                showAlert("Failed to update the product.");
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    private void showErrorAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An Error Occurred");
        alert.setContentText(content);
        alert.showAndWait();
    }
}