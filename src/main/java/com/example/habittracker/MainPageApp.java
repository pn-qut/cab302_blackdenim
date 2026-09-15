package com.example.habittracker;

import java.io.IOException;

import com.example.habittracker.controller.MainShellController;
import com.example.habittracker.model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainPageApp {
    public static final String TITLE = "Habit Tracker";
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 500;

    /**
     * Loads the main window into the given stage. Used to transition into the
     * main application from another screen (e.g. after a successful login).
     */
    public static void showOn(Stage stage, User user) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainPageApp.class.getResource("mainpage.fxml"));

        Parent root = fxmlLoader.load();

        MainShellController controller = fxmlLoader.getController();
        controller.setUser(user);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }
}
