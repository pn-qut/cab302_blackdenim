package com.example.habittracker.controller;

import com.example.habittracker.model.DAOFactory;
import com.example.habittracker.model.Habit;
import com.example.habittracker.model.HabitService;
import com.example.habittracker.model.TimeOfDay;
import com.example.habittracker.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TodayHabitController {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("EEEE d MMMM");

    @FXML
    private Label dateLabel;

    @FXML
    private Label greetingLabel;

    @FXML
    private ListView<Habit> todayHabitsList;

    private Runnable onAddHabitAction;

    private final HabitService habitService = DAOFactory.createHabitService();

    private int loggedInUserId;

    private final ObservableList<Habit> todayHabits = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        todayHabitsList.setItems(todayHabits);
        todayHabitsList.setCellFactory(list -> new HabitCell(habitService, () -> loggedInUserId));
    }

    public void setUser(User user) {
        loggedInUserId = user.getId();
        todayHabits.setAll(habitService.getUserHabits(loggedInUserId));
        updateHeader(user);
    }

    private void updateHeader(User user) {
        dateLabel.setText(LocalDate.now().format(DATE_FORMAT));
        greetingLabel.setText(TimeOfDay.greetingFor(LocalTime.now()) + ", " + user.getUsername() + "!");
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
