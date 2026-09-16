package com.example.habittracker.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SqliteHabitEntryDAO implements IHabitEntryDAO {
    private Connection connection;

    public SqliteHabitEntryDAO() {
        connection = SqliteConnection.getInstance();
        createTable();
    }

    private void createTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS habit_entries ("
                    + "user_id INTEGER NOT NULL, "
                    + "habit_id INTEGER NOT NULL, "
                    + "completion_date TEXT NOT NULL, "
                    + "PRIMARY KEY (user_id, habit_id, completion_date), "
                    + "FOREIGN KEY (user_id) REFERENCES users(user_id), "
                    + "FOREIGN KEY (habit_it) REFERENCES habits(habit_id)"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isHabitCompleted(int userId, int habitId, LocalDate date) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT EXISTS (SELECT 1 FROM habit_entries "
                            + "WHERE user_id = ? "
                            + "AND habit_id = ? "
                            + "AND completion_date = ?"
                            + ")"
            );

            statement.setInt(1, userId);
            statement.setInt(2, habitId);
            statement.setString(3, date.toString());

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void completeHabit(int userId, int habitId, LocalDate date) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO user_entries (user_id, habit_id, completion_date) VALUES (?, ?, ?)"
            );
            statement.setInt(1, userId);
            statement.setInt(2, habitId);
            statement.setString(3, date.toString());
            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void uncompleteHabit(int userId, int habitId, LocalDate date) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM habit_entries WHERE user_id = ? AND habit_id = ? AND completion_date = ?"
            );
            statement.setInt(1, userId);
            statement.setInt(2, habitId);
            statement.setString(3, date.toString());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Integer> getCompletedHabitIds(int userId, LocalDate date) {
        List<Integer> habitIds = new ArrayList<>();

        try {
            String query = "SELECT habit_id, "
                    + "FROM habit_completions "
                    + "WHERE user_id = ? "
                    + "AND completed_date = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, userId);
            statement.setString(3, date.toString());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("habit_id");
                habitIds.add(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return habitIds;
    }
}
