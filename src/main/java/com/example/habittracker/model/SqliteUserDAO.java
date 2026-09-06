package com.example.habittracker.model;

import com.example.habittracker.database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SQLite implementation of IUserDAO. Reads and writes users in the "users" table.
 */
public class SqliteUserDAO implements IUserDAO {

    private final DatabaseManager databaseManager;

    /**
     * Creates the DAO and makes sure the database tables exist.
     * @param databaseManager The manager used to open connections to the database.
     */
    public SqliteUserDAO(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        this.databaseManager.initializeSchema();
    }

    /**
     * Retrieves a user from the database, searching by username.
     * @param username The username of the user to be retrieved.
     * @return The matching user, or null if no user has that username.
     */
    @Override
    public User findByUsername(String username) {
        String sql = "SELECT username, password FROM users WHERE username = ?";
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(resultSet.getString("username"), resultSet.getString("password"));
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find user by username: " + username, e);
        }
    }

    /**
     * Checks if a username exists in the database.
     * @param username The username to be searched for in the database.
     * @return True if a user with that username already exists.
     */
    @Override
    public boolean usernameExists(String username) {
        String sql = "SELECT 1 FROM users WHERE username = ? LIMIT 1";
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check if username exists: " + username, e);
        }
    }

    /**
     * Adds a new user to the database.
     * Usernames are unique, so adding a username that already exists will fail.
     * @param user The user to add.
     */
    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add user: " + user.getUsername(), e);
        }
    }
}
