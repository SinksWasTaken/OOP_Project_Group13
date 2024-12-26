package com.example.group13.Models;

import com.example.group13.Views.ViewManager;

public class Model {
    private static Model model;
    private final ViewManager viewManager;

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
}
