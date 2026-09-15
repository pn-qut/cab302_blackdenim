package com.example.habittracker.model;
import java.util.List;

public interface IUserHabitDAO {
    // TODO: getUserHabits()
    public List<Habit> getUserHabits(int userId);

    // TODO: addHabitToUser()
    public void addHabitToUser(int userId, int habitId);

    // TODO: removeHabitFromUser()
    public void removeHabitFromUser(int userId, int habitId);
}
