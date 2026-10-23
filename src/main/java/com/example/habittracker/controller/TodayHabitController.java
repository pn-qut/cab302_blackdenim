package com.example.habittracker.controller;

import com.example.habittracker.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
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
    private Label scoreLabel;

    @FXML
    private ListView<Habit> todayHabitsList;

    @FXML
    private VBox weeklyProgressBox;

    @FXML
    private ListView<Habit> communityGoal;

    private Runnable onAddHabitAction;

    private final HabitService habitService = DAOFactory.createHabitService();

    private final IHabitDAO habitDAO = DAOFactory.getHabitDAO();

    private final ICommunityHabitDAO communityHabitDAO = DAOFactory.getCommunityHabitEntryDAO();

    private int loggedInUserId;

    private final ObservableList<Habit> todayHabits = FXCollections.observableArrayList();

    private final ObservableList<Habit> communityHabitList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        todayHabitsList.setItems(todayHabits);
        communityGoal.setItems(communityHabitList);
        todayHabitsList.setCellFactory(list -> new HabitCell(habitService, () -> loggedInUserId, this::lastDoneText));
        communityGoal.setCellFactory(list -> new HabitCell(habitService, () -> loggedInUserId, this::lastDoneText));
        habitService.attachObserver(this);
    }

    @Override
    public void onHabitsChanged() {
        todayHabitsList.refresh();
        communityGoal.refresh();
        updateWeeklyProgress();
        updateScore();
    }

    private String lastDoneText(Habit habit) {
        LocalDate lastDone = habitService.getLastCompletionDate(loggedInUserId, habit.getId());
        return LastDoneText.describe(lastDone, LocalDate.now());
    }

    public void setUser(User user) {
        loggedInUserId = user.getId();
        todayHabits.setAll(habitService.getUserHabits(loggedInUserId));
        Integer communityHabitID = communityHabitDAO.getCommunityHabitID(user.getPostcode());
        if(communityHabitID != null)
        {
            if(communityHabitList.isEmpty())
            {
                communityHabitList.add(habitDAO.findHabitById(communityHabitID));
            }
            else{
                communityHabitList.set(0,habitDAO.findHabitById(communityHabitID));
            }

        }
        updateHeader(user);
        updateWeeklyProgress();
        updateScore();
    }

    private void updateScore() {
        LocalDate today = LocalDate.now();
        int score = habitService.getUserMonthlyScore(loggedInUserId, today.getMonthValue(), today.getYear());
        scoreLabel.setText("Score: " + score);
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
        countLabel.setMinWidth(Region.USE_PREF_SIZE);

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
