package com.example.habittracker.model;

import java.time.LocalDate;
import java.util.List;

/**
 * Handles the logic for a user's habits and their completions,
 * so controllers do not talk to the DAOs directly.
 */
public class HabitService {
    private final IHabitEntryDAO entryDAO;
    private final IUserHabitDAO userHabitDAO;

    public HabitService(IHabitEntryDAO entryDAO, IUserHabitDAO userHabitDAO) {
        this.entryDAO = entryDAO;
        this.userHabitDAO = userHabitDAO;
    }

    /**
     * Gets all habits the user has selected.
     * @param userId The id of the user.
     */
    public List<Habit> getUserHabits(int userId) {
        return userHabitDAO.getUserHabits(userId);
    }

    /**
     * Adds a habit to the user's selected habits.
     * @param userId The id of the user.
     * @param habitId The id of the habit to add.
     */
    public void addHabitToUser(int userId, int habitId) {
        userHabitDAO.addHabitToUser(userId, habitId);
    }

    /**
     * Removes a habit from the user's selected habits.
     * @param userId The id of the user.
     * @param habitId The id of the habit to remove.
     */
    public void removeHabitFromUser(int userId, int habitId) {
        userHabitDAO.removeHabitFromUser(userId, habitId);
    }

    /**
     * Checks if the user completed a habit on a given date.
     * @param userId The id of the user.
     * @param habitId The id of the habit.
     * @param date The date to check.
     */
    public boolean isHabitCompleted(int userId, int habitId, LocalDate date) {
        return entryDAO.isHabitCompleted(userId, habitId, date);
    }

    /**
     * Marks a habit as completed or not completed on a given date.
     * @param userId The id of the user.
     * @param habitId The id of the habit.
     * @param date The date of the completion.
     * @param completed True to mark the habit as completed, false to remove the completion.
     */
    public void setHabitCompleted(int userId, int habitId, LocalDate date, boolean completed) {
        if (completed) {
            entryDAO.completeHabit(userId, habitId, date);
        } else {
            entryDAO.uncompleteHabit(userId, habitId, date);
        }
    }
}
