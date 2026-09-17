package com.example.habittracker.controller;

import com.example.habittracker.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

import java.time.LocalDate;
import java.util.List;

public class HabitsController {

    private IHabitDAO habitDAO;
    private IUserHabitDAO userSelectedHabitsDAO;
    private IHabitEntryDAO habitEntryDAO;

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
        habitDAO = new SqliteHabitDAO();
        userSelectedHabitsDAO = new SqliteUserSelectedHabitsDAO();
        habitEntryDAO = new SqliteHabitEntryDAO();

        currentHabitsList.setItems(currentHabits);
        currentHabitsList.setCellFactory(list -> new HabitCell());

        currentHabits.addListener((javafx.collections.ListChangeListener<Habit>) change -> updateHabitsCount());

        updateHabitsCount();
    }

    public void setUser(User user) {
        loggedInUserId = user.getId();
        currentHabits.setAll(userSelectedHabitsDAO.getUserHabits(loggedInUserId));
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

        Button addButton = new Button();
        StackPane.setAlignment(addButton, Pos.BOTTOM_CENTER);
        StackPane.setMargin(addButton, new Insets(8));

        addButton.setText(isCurrentHabit(habit.getName()) ? "Remove" : "Add");

        addButton.setOnAction(e -> {
            if (isCurrentHabit(habit.getName())) {
                userSelectedHabitsDAO.removeHabitFromUser(loggedInUserId, habit.getId());
                currentHabits.removeIf(h -> h.getName().equals(habit.getName()));
                addButton.setText("Add");
            } else {
                userSelectedHabitsDAO.addHabitToUser(loggedInUserId, habit.getId());
                currentHabits.add(habit);
                addButton.setText("Remove");
            }
        });

        card.getChildren().addAll(nameLabel, addButton);
        return card;
    }

    private boolean isCurrentHabit(String habitName) {
        return currentHabits.stream().anyMatch(h -> h.getName().equals(habitName));
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
