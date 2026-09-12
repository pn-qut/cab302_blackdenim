package com.example.habittracker.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class TodayHabitController {
    @FXML
    private ListView<String> todayList;

    private Runnable onSeeAllHabitsAction;

    @FXML
    public void initialize() {

        todayList.getItems().addAll(
                "Drink 2L of water",
                "Go for a walk",
                "Read for 20 minutes",
                "Exercise"
        );
    }

    public void setOnSeeAllHabits(Runnable action) {
        this.onSeeAllHabitsAction = action;
    }

    @FXML
    private void onSeeAllHabits() {
        if (onSeeAllHabitsAction != null) {
            onSeeAllHabitsAction.run();
        }
    }
}
