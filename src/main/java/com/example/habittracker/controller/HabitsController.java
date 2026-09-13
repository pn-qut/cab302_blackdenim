package com.example.habittracker.controller;

import com.example.habittracker.model.Habit;
import javafx.beans.property.BooleanProperty;
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

import java.util.List;

public class HabitsController {

    private static final List<String> PREMADE_HABITS = List.of(
            "Drink 2L of water",
            "Go for a walk",
            "Read for 20 minutes",
            "Exercise",
            "Meditate",
            "Sleep 8 hours"
    );

    @FXML
    private ListView<Habit> currentHabitsList;

    @FXML
    private Label currentHabitsCountLabel;

    @FXML
    private GridPane premadeHabitsPane;

    private static final int PREMADE_COLUMNS = 2;

    private final ObservableList<Habit> currentHabits = FXCollections.observableArrayList(
            new Habit("Drink 2L of water"),
            new Habit("Go for a walk"),
            new Habit("Read for 20 minutes")
    );

    @FXML
    public void initialize() {
        currentHabitsList.setItems(currentHabits);
        currentHabitsList.setCellFactory(list -> new HabitCell());

        currentHabits.addListener((javafx.collections.ListChangeListener<Habit>) change -> updateHabitsCount());
        updateHabitsCount();

        buildPremadeHabits();
    }

    private void updateHabitsCount() {
        currentHabitsCountLabel.setText(currentHabits.size() + " habits");
    }

    private void buildPremadeHabits() {
        premadeHabitsPane.getChildren().clear();
        for (int i = 0; i < PREMADE_HABITS.size(); i++) {
            String habitName = PREMADE_HABITS.get(i);
            int row = i / PREMADE_COLUMNS;
            int col = i % PREMADE_COLUMNS;
            premadeHabitsPane.add(createPremadeCard(habitName), col, row);
        }
    }

    private StackPane createPremadeCard(String habitName) {
        StackPane card = new StackPane();
        card.setPrefSize(150, 110);
        card.setMaxWidth(Double.MAX_VALUE);
        card.setStyle("-fx-background-color: #9a9a9a; -fx-background-radius: 6;");
        GridPane.setHgrow(card, Priority.ALWAYS);

        Label nameLabel = new Label(habitName);
        nameLabel.setWrapText(true);
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        nameLabel.setMaxWidth(120);
        StackPane.setAlignment(nameLabel, Pos.CENTER);

        Button addButton = new Button("Add");
        StackPane.setAlignment(addButton, Pos.BOTTOM_CENTER);
        StackPane.setMargin(addButton, new Insets(8));

        boolean alreadyAdded = isCurrentHabit(habitName);
        addButton.setDisable(alreadyAdded);
        if (alreadyAdded) {
            addButton.setText("Added");
        }

        addButton.setOnAction(e -> {
            if (!isCurrentHabit(habitName)) {
                currentHabits.add(new Habit(habitName));
            }
            addButton.setDisable(true);
            addButton.setText("Added");
        });

        card.getChildren().addAll(nameLabel, addButton);
        return card;
    }

    private boolean isCurrentHabit(String habitName) {
        return currentHabits.stream().anyMatch(h -> h.getName().equals(habitName));
    }

    private static class HabitCell extends ListCell<Habit> {
        private final CheckBox checkBox = new CheckBox();
        private final Label nameLabel = new Label();
        private final HBox root = new HBox(10, checkBox, nameLabel);
        private BooleanProperty boundProperty;

        HabitCell() {
            root.setAlignment(Pos.CENTER_LEFT);
        }

        @Override
        protected void updateItem(Habit habit, boolean empty) {
            super.updateItem(habit, empty);

            if (boundProperty != null) {
                checkBox.selectedProperty().unbindBidirectional(boundProperty);
                boundProperty = null;
            }

            if (empty || habit == null) {
                setGraphic(null);
            } else {
                nameLabel.setText(habit.getName());
                boundProperty = habit.completedTodayProperty();
                checkBox.setSelected(habit.isCompletedToday());
                checkBox.selectedProperty().bindBidirectional(boundProperty);
                setGraphic(root);
            }
        }
    }
}
