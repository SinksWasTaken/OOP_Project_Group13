package com.group13.Models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Product {
    private String productName;
    private double price;
    private String productImagePath;
    private int stockCount;
    private ImageView productImageView;

    public Product(String productName, double price, String productImagePath, int stockCount) {
        this.productName = productName;
        this.price = price;
        this.productImagePath = productImagePath;
        this.stockCount = stockCount;
        this.productImageView = new ImageView(new Image("file:" + productImagePath, 60, 60, true, true));
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductImagePath() {
        return productImagePath;
    }

    public void setProductImagePath(String productImagePath) {
        this.productImagePath = productImagePath;
        this.productImageView.setImage(new Image("file:" + productImagePath, 60, 60, true, true));
    }

    public int getStockCount() {
        return stockCount;
    }

    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }

    public ImageView getProductImageView() {
        return productImageView;
    }

    public void setProductImageView(ImageView productImageView) {
        this.productImageView = productImageView;
    }
}