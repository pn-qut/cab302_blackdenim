package com.example.habittracker;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginUI extends Application {
    public static final String TITLE = "EcoHabit Tracker";
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 500;

    @Override
   public void start(Stage stage) throws IOException {
        showOn(stage);
    }

    /**
     * Shows the login screen on the given stage. Used at startup and after logging out.
     */
    public static void showOn(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginUI.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        applyStyles(scene);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    /**
     * Applies the shared app stylesheet to a login or registration scene.
     */
    public static void applyStyles(Scene scene) {
        scene.getStylesheets().add(LoginUI.class.getResource("style.css").toExternalForm());
    }
}