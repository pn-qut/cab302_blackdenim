package com.example.habittracker.model;

import java.util.List;

/**
 * Interface for the Habit Data Access Object that handles
 * the operations for the list of default habits within the database.
 */
public interface IHabitDAO {
    /**
     * Retrieves all default habits from the database.
     * @return A list of all default habits in the database.
     */
    public List<Habit> getAllHabits();

    /**
     * Retrieves a habit from the default habits database, searching by id.
     * @param id The id number of the habit to be retrieved.
     */
    public Habit findHabitById(int id);
}
