package com.example.habittracker.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Keeps one shared connection to the database file that the whole app uses.
 * This is the Singleton pattern - there is only ever one connection.
 */
public class SqliteConnection {
    private static Connection instance = null;

    private SqliteConnection() {
        String url = "jdbc:sqlite:habit-tracker.db";
        try {
            instance = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException("Could not connect to the database", e);
        }
    }

    /**
     * Gets the shared connection, making it the first time it is asked for.
     */
    public static Connection getInstance() {
        if (instance == null) {
            new SqliteConnection();
        }
        return instance;
    }
}
