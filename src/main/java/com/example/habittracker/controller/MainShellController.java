package com.example.habittracker.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainShellController {

    @FXML
    private StackPane contentArea;

    @FXML
    private void HomeBar() {
        loadPage("todayhabit.fxml");
    }

    @FXML
    private void HabitsBar() {
        // Add later
    }

    @FXML
    private void CommunityGoalsBar() {
        // Add later
    }

    @FXML
    private void LeaderboardBar() {
        // Add later
    }

    @FXML
    private void AchievementsBar() {
        // Add later
    }

    @FXML
    private void ProfileBar() {
        // Add later
    }

    @FXML
    private void SettingsBar() {
        // Add later
    }

    private void loadPage(String fxml) {
        try {
            Node page = FXMLLoader.load(
                    getClass().getResource(fxml)
            );

            contentArea.getChildren().setAll(page);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}