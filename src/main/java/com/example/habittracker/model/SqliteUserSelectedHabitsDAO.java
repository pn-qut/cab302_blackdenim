package com.example.habittracker.model;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class SqliteUserSelectedHabitsDAO implements IUserHabitDAO{
    private Connection connection;

    public SqliteUserSelectedHabitsDAO(){
        connection = SqliteConnection.getInstance();
        createTable();
    }

    public void createTable() {

    }


    @Override
    public List<Habit> getUserHabits(int user_id) {
        return List.of();
    }

    @Override
    public void addHabitToUser(Habit habit) {

    }

    @Override
    public void removeHabitFromUser(Habit habit) {

    }
}
