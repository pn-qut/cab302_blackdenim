package com.example.habittracker.controller;

import com.example.habittracker.model.*;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class LeaderboardController {
    @FXML
    private ListView<LeaderboardEntry> leaderboardList;
    @FXML
    private Label leaderboardTitle;

    private IHabitEntryDAO habitEntryDAO;

    @FXML
    public void initialize() {
        habitEntryDAO = new SqliteHabitEntryDAO();
        leaderboardList.setCellFactory(list -> new LeaderboardCell());
        loadLeaderboard();
    }

    private void loadLeaderboard(){
        LocalDate today = LocalDate.now();

        String monthName = (today.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        leaderboardTitle.setText(monthName + " Leaderboard");

        List<LeaderboardEntry> entries = habitEntryDAO.getMonthlyLeaderboard(today.getMonthValue(), today.getYear());
        leaderboardList.getItems().setAll(entries);
    }

    private class LeaderboardCell extends ListCell<LeaderboardEntry>{
        private final Label usernameLabel = new Label();
        private final Label scoreLabel = new Label();
        private final Region spacer = new Region();
        private final HBox root = new HBox(10, usernameLabel, spacer, scoreLabel);

        LeaderboardCell(){
            root.setAlignment(Pos.CENTER_LEFT);
            HBox.setHgrow(spacer, Priority.ALWAYS);
        }

        @Override
        protected void updateItem(LeaderboardEntry entry, boolean empty) {
            super.updateItem(entry, empty);

            if (empty || entry == null) {
                setGraphic(null);
            } else {
                usernameLabel.setText(entry.getUsername());
                scoreLabel.setText(entry.getScore() + " points");

                setGraphic(root);
            }
        }
    }

}
