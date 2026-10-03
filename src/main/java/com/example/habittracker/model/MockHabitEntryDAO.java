package com.example.habittracker.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MockHabitEntryDAO implements IHabitEntryDAO {
    private final List<Entry> entries = new ArrayList<>();

    /**
     * Checks if a habit was completed by a user on a given date.
     */
    @Override
    public boolean isHabitCompleted(int userId, int habitId, LocalDate date) {
        return entries.contains(new Entry(userId, habitId, date));
    }

    /**
     * Records a habit as completed by a user on a given date.
     */
    @Override
    public void completeHabit(int userId, int habitId, LocalDate date) {
        if (!isHabitCompleted(userId, habitId, date)) {
            entries.add(new Entry(userId, habitId, date));
        }
    }

    /**
     * Removes the completion of a habit by a user on a given date.
     */
    @Override
    public void uncompleteHabit(int userId, int habitId, LocalDate date) {
        entries.remove(new Entry(userId, habitId, date));
    }

    /**
     * Gets the ids of all habits completed by a user on a given date.
     */
    @Override
    public List<Integer> getCompletedHabitIds(int userId, LocalDate date) {
        List<Integer> habitIds = new ArrayList<>();
        for (Entry entry : entries) {
            if (entry.userId == userId && entry.date.equals(date)) {
                habitIds.add(entry.habitId);
            }
        }
        return habitIds;
    }

    /**
     * Gets how many days each habit was completed by a user between two dates (inclusive).
     */
    @Override
    public Map<Integer, Integer> countCompletionsByHabit(int userId, LocalDate from, LocalDate to) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (Entry entry : entries) {
            boolean inRange = !entry.date.isBefore(from) && !entry.date.isAfter(to);
            if (entry.userId == userId && inRange) {
                counts.merge(entry.habitId, 1, Integer::sum);
            }
        }
        return counts;
    }

    private static class Entry {
        private final int userId;
        private final int habitId;
        private final LocalDate date;

        Entry(int userId, int habitId, LocalDate date) {
            this.userId = userId;
            this.habitId = habitId;
            this.date = date;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) other;
            return userId == entry.userId && habitId == entry.habitId && date.equals(entry.date);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, habitId, date);
        }
    }
}
