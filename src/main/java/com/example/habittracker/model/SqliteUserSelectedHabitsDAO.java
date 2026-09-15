package com.example.habittracker.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqliteUserSelectedHabitsDAO implements IUserHabitDAO{
    private Connection connection;

    public SqliteUserSelectedHabitsDAO(){
        connection = SqliteConnection.getInstance();
        createTable();
    }

    public void createTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS user_selected_habits ("
                    + "user_id INT NOT NULL, "
                    + "habit_id INT NOT NULL, "
                    + "PRIMARY KEY (user_id, habit_id), "
                    + "FOREIGN KEY (user_id) REFERENCES users(user_id), "
                    + "FOREIGN KEY (habit_id) REFERENCES habits(habit_id)"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Habit> getUserHabits(int userId) {
        List<Habit> habits = new ArrayList<>();

        try {
            String query = "SELECT habits.habit_id, habits.name "
                    + "FROM user_selected_habits JOIN habits "
                    + "ON habits.habit_id = user_selected_habits.habit_id "
                    + "WHERE user_selected_habits.user_id = ? "
                    + "ORDER BY habits.name";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("habit_id");
                String name = resultSet.getString("name");
                habits.add( new Habit(id, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return habits;
    }

    @Override
    public void addHabitToUser(int userId, int habitId) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO user_selected_habits (user_id, habit_id) VALUES (?, ?)"
            );
            statement.setInt(1, userId);
            statement.setInt(2, habitId);
            statement.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeHabitFromUser(int userId, int habitId) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM user_selected_habits WHERE user_id = ? AND habit_id = ?");
            statement.setInt(1, userId);
            statement.setInt(2, habitId);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
