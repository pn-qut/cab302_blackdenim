package com.example.habittracker.controller;

import com.example.habittracker.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

import java.util.List;

/**
 * Controller for the Habit List page
 */
public class HabitsController {

    private final IHabitDAO habitDAO = DAOFactory.getHabitDAO();
    private final HabitService habitService = DAOFactory.createHabitService();

    private int loggedInUserId;

    @FXML
    private ListView<Habit> currentHabitsList;

    @FXML
    private Label currentHabitsCountLabel;

    @FXML
    private GridPane premadeHabitsPane;

    private static final int PREMADE_COLUMNS = 2;

    private final ObservableList<Habit> currentHabits = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        currentHabitsList.setItems(currentHabits);
        currentHabitsList.setCellFactory(list -> new HabitCell(habitService, () -> loggedInUserId));

        currentHabits.addListener((javafx.collections.ListChangeListener<Habit>) change -> updateHabitsCount());

        updateHabitsCount();
    }

    public void setUser(User user) {
        loggedInUserId = user.getId();
        currentHabits.setAll(habitService.getUserHabits(loggedInUserId));
        buildPremadeHabits(habitDAO.getAllHabits());
    }

    private void updateHabitsCount() {
        currentHabitsCountLabel.setText(currentHabits.size() + " habits");
    }

    private void buildPremadeHabits(List<Habit> habits) {
        premadeHabitsPane.getChildren().clear();
        for (int i = 0; i < habits.size(); i++) {
            Habit habit = habits.get(i);
            int row = i / PREMADE_COLUMNS;
            int col = i % PREMADE_COLUMNS;
            premadeHabitsPane.add(createPremadeCard(habit), col, row);
        }
    }

    private StackPane createPremadeCard(Habit habit) {
        StackPane card = new StackPane();
        card.setPrefSize(150, 110);
        card.setMaxWidth(Double.MAX_VALUE);
        card.setStyle("-fx-background-color: #9a9a9a; -fx-background-radius: 6;");
        GridPane.setHgrow(card, Priority.ALWAYS);

        Label nameLabel = new Label(habit.getName());
        nameLabel.setWrapText(true);
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        nameLabel.setMaxWidth(120);
        StackPane.setAlignment(nameLabel, Pos.CENTER);

        Button toggleButton = new Button();
        StackPane.setAlignment(toggleButton, Pos.BOTTOM_CENTER);
        StackPane.setMargin(toggleButton, new Insets(8));

        toggleButton.setText(isCurrentHabit(habit.getName()) ? "Remove" : "Add");

        toggleButton.setOnAction(e -> {
            if (isCurrentHabit(habit.getName())) {
                habitService.removeHabitFromUser(loggedInUserId, habit.getId());
                currentHabits.removeIf(h -> h.getName().equals(habit.getName()));
                toggleButton.setText("Add");
            } else {
                habitService.addHabitToUser(loggedInUserId, habit.getId());
                currentHabits.add(habit);
                toggleButton.setText("Remove");
            }
        });

        card.getChildren().addAll(nameLabel, toggleButton);
        return card;
    }

    private boolean isCurrentHabit(String habitName) {
        return currentHabits.stream().anyMatch(h -> h.getName().equals(habitName));
    }
}
