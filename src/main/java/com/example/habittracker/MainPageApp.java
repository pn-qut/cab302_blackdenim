package com.example.habittracker;

import java.io.IOException;

import com.example.habittracker.controller.MainShellController;
import com.example.habittracker.model.User;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainPageApp {
    public static final String TITLE = "Habit Tracker";
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 800;
    public static final String STYLESHEET = "style.css";

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
        scene.getStylesheets().add(MainPageApp.class.getResource(STYLESHEET).toExternalForm());
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }
}
