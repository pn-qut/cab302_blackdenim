package com.example.habittracker.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;

import java.io.IOException;

public class MainShellController {

    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {
        loadPage("todayhabit.fxml");
    }

    @FXML
    private void HomeBar() {
        loadPage("todayhabit.fxml");
    }

    @FXML
    private void HabitsBar() {
        loadPage("habits.fxml");
    }

    @FXML
    private void CommunityGoalsBar() {
        contentArea.getChildren().setAll(new Label("Community goal page is coming soon!"));
    }

    @FXML
    private void LeaderboardBar() {
        contentArea.getChildren().setAll(new Label("Leaderboard page is coming soon!"));
    }

    @FXML
    private void AchievementsBar() {
        contentArea.getChildren().setAll(new Label("Achievement page is coming soon!"));
    }

    @FXML
    private void ProfileBar() {
        contentArea.getChildren().setAll(new Label("Profile page is coming soon!"));
    }

    @FXML
    private void SettingsBar() {
        contentArea.getChildren().setAll(new Label("Setting page is coming soon!"));
    }

    private void loadPage(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/habittracker/" + path)
            );
            Node page = loader.load();

            Object controller = loader.getController();
            if (controller instanceof TodayHabitController) {
                ((TodayHabitController) controller).setOnSeeAllHabits(() -> loadPage("habits.fxml"));
            }

            contentArea.getChildren().setAll(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}