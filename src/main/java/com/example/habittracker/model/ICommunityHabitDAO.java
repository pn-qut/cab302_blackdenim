package com.example.habittracker.model;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface for the Habit Data Access Object that handles
 * the operations for the list of default habits within the database.
 */
public interface ICommunityHabitDAO {

    public Integer getCommunityHabitID(int postcode);

    public int getTotalCommunityCompletions(int postcode, LocalDate date);
}
