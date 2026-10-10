package com.example.habittracker.model;

/**
 * Implemented by anything that needs to update when a user's habits change,
 * such as a page showing habit progress.
 */
public interface HabitObserver {
    /**
     * Called after a habit is completed or uncompleted.
     */
    void onHabitsChanged();
}
