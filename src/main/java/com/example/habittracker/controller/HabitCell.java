package com.example.habittracker.controller;

import java.time.LocalDate;
import java.util.function.Function;
import java.util.function.IntSupplier;

import com.example.habittracker.model.Habit;
import com.example.habittracker.model.HabitService;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * List cell that shows a habit with a checkbox for marking it as completed today,
 * and optionally some detail text on the right.
 */
public class HabitCell extends ListCell<Habit> {
    private final HabitService habitService;
    private final IntSupplier userIdSupplier;
    private final Function<Habit, String> detailText;

    private final CheckBox checkBox = new CheckBox();
    private final Label nameLabel = new Label();
    private final Region spacer = new Region();
    private final Label detailLabel = new Label();
    private final HBox root = new HBox(10, checkBox, nameLabel, spacer, detailLabel);

    /**
     * Creates a cell with no detail text.
     */
    public HabitCell(HabitService habitService, IntSupplier userIdSupplier) {
        this(habitService, userIdSupplier, habit -> null);
    }

    /**
     * Creates a cell that shows detail text on the right, or nothing when the text is null.
     */
    public HabitCell(HabitService habitService, IntSupplier userIdSupplier, Function<Habit, String> detailText) {
        this.habitService = habitService;
        this.userIdSupplier = userIdSupplier;
        this.detailText = detailText;

        detailLabel.getStyleClass().add("habit-detail");
        HBox.setHgrow(spacer, Priority.ALWAYS);
        root.setAlignment(Pos.CENTER_LEFT);
        checkBox.setOnAction(e -> onCheckBoxToggled());
    }

    @Override
    protected void updateItem(Habit habit, boolean empty) {
        super.updateItem(habit, empty);

        if (empty || habit == null) {
            setGraphic(null);
        } else {
            nameLabel.setText(habit.getName());
            detailLabel.setText(detailText.apply(habit));
            checkBox.setSelected(habitService.isHabitCompleted(userIdSupplier.getAsInt(), habit.getId(), LocalDate.now()));
            setGraphic(root);
        }
    }

    private void onCheckBoxToggled() {
        Habit habit = getItem();
        if (habit == null) {
            return;
        }
        setCompletedToday(habit, checkBox.isSelected());
    }

    private void setCompletedToday(Habit habit, boolean completed) {
        habitService.setHabitCompleted(userIdSupplier.getAsInt(), habit.getId(), LocalDate.now(), completed);
    }
}
