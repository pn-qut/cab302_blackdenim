package com.example.habittracker.controller;

import com.example.habittracker.model.Habit;
import com.example.habittracker.model.HabitService;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.util.function.IntSupplier;

/**
 * List cell that shows a habit with a checkbox for marking it as completed today.
 */
public class HabitCell extends ListCell<Habit> {
    private final HabitService habitService;
    private final IntSupplier userIdSupplier;

    private final CheckBox checkBox = new CheckBox();
    private final Label nameLabel = new Label();
    private final HBox root = new HBox(10, checkBox, nameLabel);

    /**
     * @param habitService The service used to read and save habit completions.
     * @param userIdSupplier Supplies the id of the logged in user when it is needed.
     */
    public HabitCell(HabitService habitService, IntSupplier userIdSupplier) {
        this.habitService = habitService;
        this.userIdSupplier = userIdSupplier;

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
