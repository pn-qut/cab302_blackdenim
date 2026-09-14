package com.example.habittracker;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainPageApp extends Application {
    public static final String TITLE = "Habit Tracker";
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 500;

    @Override
    public void start(Stage stage) throws IOException {
        showOn(stage);
    }

    /**
     * Loads the main window into the given stage. Used to transition into the
     * main application from another screen (e.g. after a successful login).
     */
    public static void showOn(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainPageApp.class.getResource("mainpage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }
}
