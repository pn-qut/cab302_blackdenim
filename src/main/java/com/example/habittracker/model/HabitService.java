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
    private static final int DAYS_IN_WEEK = 7;

    private final IHabitEntryDAO entryDAO;
    private final IUserHabitDAO userHabitDAO;
    private final IHabitDAO habitDAO;
    private final List<HabitObserver> observers = new ArrayList<>();

    public HabitService(IHabitEntryDAO entryDAO, IUserHabitDAO userHabitDAO, IHabitDAO habitDAO) {
        this.entryDAO = entryDAO;
        this.userHabitDAO = userHabitDAO;
        this.habitDAO=habitDAO;
    }

    /**
     * Registers an observer to be told whenever a habit is completed or uncompleted.
     */
    public void attachObserver(HabitObserver observer) {
        observers.add(observer);
    }

    /**
     * Stops an observer from being told about habit changes.
     */
    public void detachObserver(HabitObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (HabitObserver observer : observers) {
            observer.onHabitsChanged();
        }
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
        notifyObservers();
    }

    /**
     * Gets the most recent date the user completed a habit, or null if never completed.
     */
    public LocalDate getLastCompletionDate(int userId, int habitId) {
        return entryDAO.getLastCompletionDate(userId, habitId);
    }

    /**
     * Gets how many days each of the user's habits was completed this week, from Monday to Sunday.
     */
    public List<HabitProgress> getWeeklyProgress(int userId, LocalDate today) {
        LocalDate weekStart = startOfWeek(today);
        LocalDate weekEnd = weekStart.plusDays(DAYS_IN_WEEK - 1);
        Map<Integer, Integer> counts = entryDAO.countCompletionsByHabit(userId, weekStart, weekEnd);

        List<HabitProgress> progress = new ArrayList<>();
        for (Habit habit : userHabitDAO.getUserHabits(userId)) {
            int completedDays = counts.getOrDefault(habit.getId(), 0);
            progress.add(new HabitProgress(habit, completedDays, DAYS_IN_WEEK));
        }
        return progress;
    }

    public int getCurrentStreak(int userId, int habitId){
        return entryDAO.getCurrentStreak(userId, habitId);
    }

    /**
     * Wrapper for private entry DAO function to return user's monthly score.
     */
    public int getUserMonthlyScore(int userId, int month, int year) {
        return entryDAO.getUserMonthlyScore(userId, month, year);
    }

    /**
     * Get a habit by id
     */
    public Habit getHabitFromID(int habitID) {
        return habitDAO.findHabitById(habitID);
    }

    /**
     * Gets the Monday of the week that a date falls in.
     */
    private static LocalDate startOfWeek(LocalDate date) {
        return date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }
}
