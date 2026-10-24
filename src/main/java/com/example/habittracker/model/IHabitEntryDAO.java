package com.example.habittracker.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IHabitEntryDAO {

    // Check if a habit was completed on a given date
    boolean isHabitCompleted(int userId, int habitId, LocalDate date);

    // Mark a habit as completed on a given date
    void completeHabit(int userId, int habitId, LocalDate date);

    // Mark a habit as uncompleted on a given date
    void uncompleteHabit(int userId, int habitId, LocalDate date);

    // Get all the ids for all habits completed by a user on a given date
    List<Integer> getCompletedHabitIds(int userId, LocalDate date);

    // Get all the dates (from most recent to least recent) that a user completed at least one habit
    List<LocalDate> getActiveDates(int userId);

    // Get a users total score for a given month, where one point is given for each completed habit
    int getUserMonthlyScore(int userId, int month, int year);

    // Get a list of leaderboard entries for a given month, ranked from highest to lowest score
    List<LeaderboardEntry> getMonthlyLeaderboard(int month, int year);

    // Get how many days each habit was completed between two dates (inclusive), keyed by habit id
    Map<Integer, Integer> countCompletionsByHabit(int userId, LocalDate from, LocalDate to);

    // Get the most recent date a user completed a habit, or null if never completed
    LocalDate getLastCompletionDate(int userId, int habitId);
}
