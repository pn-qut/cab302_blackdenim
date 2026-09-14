package com.example.habittracker.model;

import java.time.LocalDate;

public class HabitEntry {
    // id, username, habit_id, date, completed
    private int id;
    private String username;
    private int habit_id;
    private LocalDate date;
    private boolean completed;
    // TODO LATER: In future sprint once option to input values as a habit entry is added
    //private int measurementValue;

    public HabitEntry(int id, String username, int habit_id, LocalDate date, boolean completed) {
        this.id = id;
        this.username = username;
        this.habit_id = habit_id;
        this.date = date;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getHabit_id() {
        return habit_id;
    }

    public void setHabit_id(int habit_id) {
        this.habit_id = habit_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
