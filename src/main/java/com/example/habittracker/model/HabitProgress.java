package com.example.habittracker.model;

/**
 * How many days a habit was completed out of a target number of days.
 */
public class HabitProgress {
    private final Habit habit;
    private final int completedDays;
    private final int targetDays;

    public HabitProgress(Habit habit, int completedDays, int targetDays) {
        this.habit = habit;
        this.completedDays = completedDays;
        this.targetDays = targetDays;
    }

    public Habit getHabit() {
        return habit;
    }

    public int getCompletedDays() {
        return completedDays;
    }

    public int getTargetDays() {
        return targetDays;
    }

    /**
     * Gets the completed days as a fraction of the target, from 0 to 1.
     */
    public double getProgress() {
        return (double) completedDays / targetDays;
    }
}
