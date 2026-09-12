package com.example.habittracker.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Saves and loads users from the SQLite database.
 */
public class SqliteUserDAO implements IUserDAO {

    private Connection connection;

    public SqliteUserDAO() {
        connection = SqliteConnection.getInstance();
        createTable();
    }

    /**
     * Makes the users table if it is not there yet.
     */
    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users ("
                + "username VARCHAR(50) PRIMARY KEY, "
                + "password VARCHAR(255) NOT NULL)";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Could not create the users table", e);
        }
    }

    /**
     * Looks up a user by their username.
     * @param username The username to look for.
     * @return The user, or null if nobody has that username.
     */
    @Override
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new User(rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not look up the user: " + username, e);
        }
        return null;
    }

    /**
     * Checks if a username is already taken.
     * @param username The username to check.
     * @return true if someone already has that username.
     */
    @Override
    public boolean usernameExists(String username) {
        return findByUsername(username) != null;
    }

    /**
     * Saves a new user to the database.
     * Usernames have to be unique, so adding one that is already taken will fail.
     * @param user The user to save.
     */
    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Could not add the user: " + user.getUsername(), e);
        }
    }
}
