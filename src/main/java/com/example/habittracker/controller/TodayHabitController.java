package com.example.habittracker.controller;

import com.example.habittracker.model.DAOFactory;
import com.example.habittracker.model.Habit;
import com.example.habittracker.model.HabitObserver;
import com.example.habittracker.model.HabitProgress;
import com.example.habittracker.model.HabitService;
import com.example.habittracker.model.TimeOfDay;
import com.example.habittracker.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TodayHabitController implements HabitObserver {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("EEEE d MMMM");
    private static final double PROGRESS_NAME_WIDTH = 90;

    @FXML
    private Label dateLabel;

    @FXML
    private Label greetingLabel;

    @FXML
    private ListView<Habit> todayHabitsList;

    @FXML
    private VBox weeklyProgressBox;

    private Runnable onAddHabitAction;

    private final HabitService habitService = DAOFactory.createHabitService();

    private int loggedInUserId;

    private final ObservableList<Habit> todayHabits = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        todayHabitsList.setItems(todayHabits);
        todayHabitsList.setCellFactory(list -> new HabitCell(habitService, () -> loggedInUserId));
        habitService.attachObserver(this);
    }

    @Override
    public void onHabitsChanged() {
        updateWeeklyProgress();
    }

    public void setUser(User user) {
        loggedInUserId = user.getId();
        todayHabits.setAll(habitService.getUserHabits(loggedInUserId));
        updateHeader(user);
        updateWeeklyProgress();
    }

    private void updateHeader(User user) {
        dateLabel.setText(LocalDate.now().format(DATE_FORMAT));
        greetingLabel.setText(TimeOfDay.greetingFor(LocalTime.now()) + ", " + user.getUsername() + "!");
    }

    private void updateWeeklyProgress() {
        weeklyProgressBox.getChildren().clear();
        for (HabitProgress progress : habitService.getWeeklyProgress(loggedInUserId, LocalDate.now())) {
            weeklyProgressBox.getChildren().add(createProgressRow(progress));
        }
    }

    private HBox createProgressRow(HabitProgress progress) {
        Label nameLabel = new Label(progress.getHabit().getName());
        nameLabel.setMinWidth(PROGRESS_NAME_WIDTH);
        nameLabel.setMaxWidth(PROGRESS_NAME_WIDTH);

        ProgressBar bar = new ProgressBar(progress.getProgress());
        bar.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(bar, Priority.ALWAYS);

        Label countLabel = new Label(progress.getCompletedDays() + "/" + progress.getTargetDays());

        HBox row = new HBox(8, nameLabel, bar, countLabel);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
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
