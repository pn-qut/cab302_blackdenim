package com.example.habittracker.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqliteHabitDAO implements IHabitDAO{
    private final Connection connection;

    public SqliteHabitDAO() {
        this(SqliteConnection.getInstance());
    }

    public SqliteHabitDAO(Connection connection) {
        this.connection = connection;
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

    private void addDefaultHabits(){
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT OR IGNORE INTO habits (name) VALUES (?)"
            );

            String[] habits = {
                    "Swap single use plastics with reusables", "Reduce food waste", "Use public transport", "Compost organic waste",
                    "Adopt meal planning", "Keep shower times short", "Reduce energy use", "Practice eco-driving", "Repair items before replacing them",
                    "Use reusable shopping bags", "Recycle household waste correctly", "Buy second-hand items", "Walk or cycle for short trips",
                    "Turn off lights when leaving a room", "Unplug electronics when not in use", "Choose locally produced food", "Air-dry clothes instead of using a dryer", 
                    "Use energy-efficient LED bulbs", "Donate unwanted clothes", "Grow vegetables at home", "Use eco-friendly cleaning products"
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
        String query = "SELECT habit_id, name FROM habits WHERE habit_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Habit(resultSet.getInt("habit_id"), resultSet.getString("name"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

