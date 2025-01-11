package com.group13.Models;

import com.group13.Views.ViewManager;

public class Model {
    private static Model model;
    private final ViewManager viewManager;
    private Worker currentWorker;
    private Worker nextWorker;
    private Product selectedProduct;
    private Prices prices;

    private Model() {
        this.viewManager = new ViewManager();
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public ViewManager getViewManager() {
        return viewManager;
    }

    public Worker getCurrentWorker() {
        return currentWorker;
    }

    public void setCurrentWorker(Worker currentWorker) {
        this.currentWorker = currentWorker;
    }

    public Worker getNextWorker() {
        return nextWorker;
    }

    public void setNextWorker(Worker nextWorker) {
        this.nextWorker = nextWorker;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public Prices getPrices() {
        return prices;
    }

    public void setPrices(Prices prices) {
        this.prices = prices;
    }
}
