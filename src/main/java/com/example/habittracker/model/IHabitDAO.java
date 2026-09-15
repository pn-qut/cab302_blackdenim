package com.example.habittracker.model;

import java.util.List;

/**
 * Interface for the Habit Data Access Object that handles
 * the operations for the list of default habits within the database.
 */
public interface IHabitDAO {
    /**
     * Retrieves the names of all default habits from the database as a list.
     * @return A list of all default habit names in the database.
     */
    public List<Habit> getAllHabits();

    /**
     * Retrieves a habit from the default habits database, searching by id.
     * @param id The id number of the habit to be retrieved.
     */
    public Habit findHabitById(int id);
}
