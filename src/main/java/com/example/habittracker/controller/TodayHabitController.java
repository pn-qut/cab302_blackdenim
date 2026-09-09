package com.example.habittracker.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class TodayHabitController {
    @FXML
    private ListView<String> todayList;

    @FXML
    public void initialize() {

        todayList.getItems().addAll(
                "Drink 2L of water",
                "Go for a walk",
                "Read for 20 minutes",
                "Exercise"
        );
    }

    @FXML
    private void onSeeAllHabits() {
        // Navigate to Habits page
    }
}
