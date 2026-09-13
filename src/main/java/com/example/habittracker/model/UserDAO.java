package com.example.habittracker.model;

import java.sql.*;

public class UserDAO {
    private Connection connection;

    public UserDAO() {
        connection = DatabaseConnection.getInstance();
    }

    public void createTable() {
        try {
            Statement createTable = connection.createStatement();
            createTable.execute(
                "CREATE TABLE IF NOT EXISTS users ("
                + "user_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "username VARCHAR NOT NULL UNIQUE, "
                + "password VARCHAR NOT NULL"
                + ")"
            );
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public User findByUsername(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT username, password FROM users WHERE username = ?"
            );
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new User(rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return null;
    }

    public boolean usernameExists(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT 1 FROM users WHERE username = ? LIMIT 1"
            );
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return false;
    }

    public void addUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO users (username, password) VALUES (?, ?)"
            );
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
}