package com.example.habittracker.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqliteHabitDAO implements IHabitDAO{
    private Connection connection;

    public SqliteHabitDAO(){
        connection = SqliteConnection.getInstance();
        createTable();
        addDefaultHabits();
    }

    private void createTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS habits ("
                    + "habit_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "name VARCHAR NOT NULL UNIQUE"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addDefaultHabits(){
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT OR IGNORE INTO habits (name) VALUES (?)"
            );

            String[] habits = {
                    "Drink water",
                    "Exercise",
                    "Read",
                    "Meditate"
            };

            for (String habit : habits) {
                statement.setString(1, habit);
                statement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Habit> getAllHabits() {
        List<Habit> habits = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM habits";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("habit_id");
                String habitName = resultSet.getString("name");
                habits.add(new Habit(id, habitName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return habits;
    }

    @Override
    public Habit findHabitById(int id) {
        return null;
    }
}
