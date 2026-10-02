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
import javafx.scene.control.ListView;

public class TodayHabitController {
    @FXML
    private ListView<Habit> todayHabitsList;

    private Runnable onAddHabitAction;

    private final IUserHabitDAO userHabitDAO = new SqliteUserSelectedHabitsDAO();
    private final IHabitEntryDAO habitEntryDAO = new SqliteHabitEntryDAO();

    private int loggedInUserId;

    private final ObservableList<Habit> todayHabits = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        todayHabitsList.setItems(todayHabits);
        todayHabitsList.setCellFactory(list -> new HabitCell(habitEntryDAO, () -> loggedInUserId));
    }

    public void setUser(User user) {
        loggedInUserId = user.getId();
        todayHabits.setAll(userHabitDAO.getUserHabits(loggedInUserId));
    }

    public void setOnAddHabit(Runnable action) {
        this.onAddHabitAction = action;
    }

    @FXML
    private void onAddHabitClicked() {
        if (onAddHabitAction != null) {
            onAddHabitAction.run();
        }
    }
}
