package com.example.habittracker.model;

import java.time.LocalDate;

public interface IHabitEntryDAO {

    // Check if a habit was completed on a given date
    boolean isHabitCompleted(int userId, int habitId, LocalDate date);

    // Mark a habit as completed on a given date
    void completeHabit(int userId, int habitId, LocalDate date);

    // Mark a habit as uncompleted on a given date
    void uncompleteHabit(int userId, int habitId, LocalDate date);

    // Get all the ids for all habits completed by a user on a given date
    void getCompletedHabitIds(int userId, LocalDate date);
}
