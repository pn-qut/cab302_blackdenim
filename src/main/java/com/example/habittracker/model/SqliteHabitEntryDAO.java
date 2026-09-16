package com.example.habittracker.model;

import java.sql.Connection;
import java.sql.Statement;

public class SqliteHabitEntryDAO {
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
}
