package com.example.habittracker.model;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class SqliteHabitDAO implements IHabitDAO{
    private Connection connection;

    public SqliteHabitDAO(){
        connection = SqliteConnection.getInstance();
        createTable();
    }

    private void createTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS habits ("
                    + "habit_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "name VARCHAR NOT NULL UNIQUE, "
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO: insert the habits into database

    @Override
    public List<Habit> getAllHabits() {
        return List.of();
    }

    @Override
    public Habit findHabitById(int id) {
        return null;
    }
}
