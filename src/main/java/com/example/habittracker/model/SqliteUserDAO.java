package com.example.habittracker.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqliteUserDAO implements IUserDAO {
    private final Connection connection;

    public SqliteUserDAO() {
        this(SqliteConnection.getInstance());
    }

    public SqliteUserDAO(Connection connection) {
        this.connection = connection;
        createTable();
    }

    private void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS users ("
                + "user_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "username VARCHAR NOT NULL UNIQUE, "
                + "password VARCHAR NOT NULL, "
                + "postcode INTEGER NOT NULL DEFAULT 0"
                + ")";
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
            addPostcodeColumnIfMissing();
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to initialize the users table.", e);
        }
    }

    private void addPostcodeColumnIfMissing() throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet columns = statement.executeQuery("PRAGMA table_info(users)")) {
            while (columns.next()) {
                if ("postcode".equalsIgnoreCase(columns.getString("name"))) {
                    return;
                }
            }
        }

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "ALTER TABLE users ADD COLUMN postcode INTEGER NOT NULL DEFAULT 0"
            );
        }
    }

    @Override
    public User findByUsername(String username) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT user_id, username, password, postcode FROM users WHERE username = ?"
        )) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("user_id");
                    String storedUsername = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    int postcode = resultSet.getInt("postcode");
                    User user = new User(storedUsername, password, postcode);
                    user.setId(id);
                    return user;
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to find user by username.", e);
        }
        return null;
    }

    @Override
    public boolean usernameExists(String username) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT 1 FROM users WHERE username = ? LIMIT 1"
        )) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to check whether the username exists.", e);
        }
    }

    @Override
    public List<String> findSuburbsByPostcode(int postcode) {
        List<String> suburbs = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT suburb FROM suburbs WHERE postcode = ? ORDER BY suburb COLLATE NOCASE"
        )) {
            statement.setInt(1, postcode);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    suburbs.add(resultSet.getString("suburb"));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to find suburbs for postcode.", e);
        }
        return suburbs;
    }

    @Override
    public void addUser(User user) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO users (username, password, postcode) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
        )) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getPostcode());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to save user.", e);
        }
    }

    @Override
    public void updatePassword(String username, String newPassword) {
    try (PreparedStatement statement = connection.prepareStatement(
            "UPDATE users SET password = ? WHERE username = ?"
    )) {
        statement.setString(1, newPassword);
        statement.setString(2, username);
        statement.executeUpdate();
    } catch (SQLException e) {
        throw new IllegalStateException("Unable to update password.", e);
    }
}
}
