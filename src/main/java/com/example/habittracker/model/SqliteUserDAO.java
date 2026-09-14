package com.example.habittracker.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SqliteUserDAO implements IUserDAO{
    private Connection connection;

    public SqliteUserDAO(){
        connection = SqliteConnection.getInstance();
        createTable();
    }

    private void createTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS users ("
                    + "user_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "username VARCHAR NOT NULL UNIQUE, "
                    + "password VARCHAR NOT NULL"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findByUsername(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM users WHERE username = ?"
            );
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("user_id");
                String password = resultSet.getString("password");
                User user = new User(username, password);
                user.setId(id);
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean usernameExists(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT 1 FROM users WHERE username = ? LIMIT 1"
            );
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void addUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users (username, password) VALUES (?, ?)"
            );
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();

            // Set the id of the new contact
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
