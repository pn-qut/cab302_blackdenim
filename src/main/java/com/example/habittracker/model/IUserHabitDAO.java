package com.example.habittracker.model;
import java.util.List;

public interface IUserHabitDAO {
    // TODO: getUserHabits()
    public List<Habit> getUserHabits(int user_id);

    // TODO: addHabitToUser()
    public void addHabitToUser(Habit habit);

    // TODO: removeHabitFromUser()
    public void removeHabitFromUser(Habit habit);
}
