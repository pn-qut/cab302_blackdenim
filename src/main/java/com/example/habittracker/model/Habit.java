package com.example.habittracker.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Habit {

    private final StringProperty name;
    private final BooleanProperty completedToday;

    public Habit(String name) {
        this(name, false);
    }

    public Habit(String name, boolean completedToday) {
        this.name = new SimpleStringProperty(name);
        this.completedToday = new SimpleBooleanProperty(completedToday);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public boolean isCompletedToday() {
        return completedToday.get();
    }

    public void setCompletedToday(boolean completedToday) {
        this.completedToday.set(completedToday);
    }

    public BooleanProperty completedTodayProperty() {
        return completedToday;
    }
}
