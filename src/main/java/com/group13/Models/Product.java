package com.group13.Models;

public class Product {

    private String productName;
    private int price;
    private String productImagePath;
    private int stockCount;

    public Product(String productName, int price, String imagePath, int stockCount) {
        this.productName = productName;
        this.price = price;
        this.productImagePath = imagePath;
        this.stockCount = stockCount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProductImagePath() {
        return productImagePath;
    }

    public void setProductImagePath(String productImagePath) {
        this.productImagePath = productImagePath;
    }

    public int getStockCount() {
        return stockCount;
    }

    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }
}
