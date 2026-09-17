package com.example.habittracker.controller;

import com.example.habittracker.model.Habit;
import com.example.habittracker.model.IHabitEntryDAO;
import com.example.habittracker.model.IUserHabitDAO;
import com.example.habittracker.model.SqliteHabitEntryDAO;
import com.example.habittracker.model.SqliteUserSelectedHabitsDAO;
import com.example.habittracker.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.time.LocalDate;

public class TodayHabitController {
    @FXML
    private ListView<Habit> todayList;

    private Runnable onSeeAllHabitsAction;

    private IUserHabitDAO userHabitDAO;
    private IHabitEntryDAO habitEntryDAO;

    private int loggedInUserId;

    private final ObservableList<Habit> todayHabits = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        userHabitDAO = new SqliteUserSelectedHabitsDAO();
        habitEntryDAO = new SqliteHabitEntryDAO();

        todayList.setItems(todayHabits);
        todayList.setCellFactory(list -> new HabitCell());
    }

    public void setUser(User user) {
        loggedInUserId = user.getId();
        todayHabits.setAll(userHabitDAO.getUserHabits(loggedInUserId));
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

    private class HabitCell extends ListCell<Habit> {
        private final CheckBox checkBox = new CheckBox();
        private final Label nameLabel = new Label();
        private final HBox root = new HBox(10, checkBox, nameLabel);

        HabitCell() {
            root.setAlignment(Pos.CENTER_LEFT);
            checkBox.setOnAction(e -> {
                Habit habit = getItem();
                if (habit == null) {
                    return;
                }
                LocalDate today = LocalDate.now();
                if (checkBox.isSelected()) {
                    habitEntryDAO.completeHabit(loggedInUserId, habit.getId(), today);
                } else {
                    habitEntryDAO.uncompleteHabit(loggedInUserId, habit.getId(), today);
                }
            });
        }

        @Override
        protected void updateItem(Habit habit, boolean empty) {
            super.updateItem(habit, empty);

            if (empty || habit == null) {
                setGraphic(null);
            } else {
                nameLabel.setText(habit.getName());
                checkBox.setSelected(habitEntryDAO.isHabitCompleted(loggedInUserId, habit.getId(), LocalDate.now()));
                setGraphic(root);
            }
        }
    }
}
