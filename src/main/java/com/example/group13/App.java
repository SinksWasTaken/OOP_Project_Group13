package com.example.group13;

import com.example.group13.Models.Model;
import com.example.group13.Views.ViewManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage){
        Model.getInstance().getViewManager().showLoginWindow();
    }
}
