package com.example.habittracker.controller;

import com.example.habittracker.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;

import java.io.IOException;

public class MainShellController {

    private static final String VIEW_FOLDER = "/com/example/habittracker/";
    private static final String HOME_VIEW = "todayhabit.fxml";
    private static final String HABITS_VIEW = "habits.fxml";

    @FXML
    private StackPane contentArea;

    private User user;

    public void setUser(User user) {
        this.user = user;
        loadPage(HOME_VIEW);
    }

    @FXML
    private void onHomeClicked() {
        loadPage(HOME_VIEW);
    }

    @FXML
    private void onHabitsClicked() {
        loadPage(HABITS_VIEW);
    }

    @FXML
    private void onCommunityGoalsClicked() {
        showComingSoon("Community goal page");
    }

    @FXML
    private void onLeaderboardClicked() {
        showComingSoon("Leaderboard page");
    }

    @FXML
    private void onAchievementsClicked() {
        showComingSoon("Achievement page");
    }

    @FXML
    private void onProfileClicked() {
        showComingSoon("Profile page");
    }

    @FXML
    private void onSettingsClicked() {
        showComingSoon("Setting page");
    }

    private void showComingSoon(String pageName) {
        contentArea.getChildren().setAll(new Label(pageName + " is coming soon!"));
    }

    private void loadPage(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(VIEW_FOLDER + path)
            );
            Node page = loader.load();

            Object controller = loader.getController();
            if (controller instanceof TodayHabitController) {
                TodayHabitController homeController = (TodayHabitController) controller;
                homeController.setOnAddHabit(() -> loadPage(HABITS_VIEW));
                homeController.setUser(user);
            } else if (controller instanceof HabitsController) {
                ((HabitsController) controller).setUser(user);
            }

            contentArea.getChildren().setAll(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
