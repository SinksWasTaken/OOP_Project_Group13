package com.group13;

import com.group13.Models.Model;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage){
        Model.getInstance().getViewManager().showLoginWindow();
    }
}
