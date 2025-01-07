package com.group13;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/admin-operation1-view.fxml"));


        Scene scene = new Scene(loader.load());

        // Set up the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cinema Management - Admin Operations");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}
