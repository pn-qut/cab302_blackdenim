package com.example.habittracker.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockUserHabitDAO implements IUserHabitDAO {
    private final List<Habit> availableHabits;
    private final Map<Integer, List<Habit>> habitsByUser = new HashMap<>();

    /**
     * @param availableHabits The habits that can be added to a user, standing in for the habits table.
     */
    public MockUserHabitDAO(List<Habit> availableHabits) {
        this.availableHabits = availableHabits;
    }

    /**
     * Gets all habits selected by a user.
     */
    @Override
    public List<Habit> getUserHabits(int userId) {
        return new ArrayList<>(habitsByUser.getOrDefault(userId, List.of()));
    }

    /**
     * Adds a habit to a user's selected habits.
     */
    @Override
    public void addHabitToUser(int userId, int habitId) {
        for (Habit habit : availableHabits) {
            if (habit.getId() == habitId) {
                habitsByUser.computeIfAbsent(userId, id -> new ArrayList<>()).add(habit);
            }
        }
    }

    /**
     * Removes a habit from a user's selected habits.
     */
    @Override
    public void removeHabitFromUser(int userId, int habitId) {
        List<Habit> habits = habitsByUser.get(userId);
        if (habits != null) {
            habits.removeIf(habit -> habit.getId() == habitId);
        }
    }
}
