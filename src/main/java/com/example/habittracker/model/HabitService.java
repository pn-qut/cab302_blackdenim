package com.example.habittracker.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
     */
    public List<Habit> getUserHabits(int userId) {
        return userHabitDAO.getUserHabits(userId);
    }

    /**
     * Adds a habit to the user's selected habits.
     */
    public void addHabitToUser(int userId, int habitId) {
        userHabitDAO.addHabitToUser(userId, habitId);
    }

    /**
     * Removes a habit from the user's selected habits.
     */
    public void removeHabitFromUser(int userId, int habitId) {
        userHabitDAO.removeHabitFromUser(userId, habitId);
    }

    /**
     * Checks if the user completed a habit on a given date.
     */
    public boolean isHabitCompleted(int userId, int habitId, LocalDate date) {
        return entryDAO.isHabitCompleted(userId, habitId, date);
    }

    /**
     * Marks a habit as completed or not completed on a given date.
     */
    public void setHabitCompleted(int userId, int habitId, LocalDate date, boolean completed) {
        if (completed) {
            entryDAO.completeHabit(userId, habitId, date);
        } else {
            entryDAO.uncompleteHabit(userId, habitId, date);
        }
    }

    /**
     * Gets how many days each of the user's habits was completed this week, from Monday to Sunday.
     */
    public List<HabitProgress> getWeeklyProgress(int userId, LocalDate today) {
        LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        Map<Integer, Integer> counts = entryDAO.countCompletionsByHabit(userId, monday, monday.plusDays(6));

        List<HabitProgress> progress = new ArrayList<>();
        for (Habit habit : userHabitDAO.getUserHabits(userId)) {
            progress.add(new HabitProgress(habit, counts.getOrDefault(habit.getId(), 0), 7));
        }
        return progress;
    }
}
