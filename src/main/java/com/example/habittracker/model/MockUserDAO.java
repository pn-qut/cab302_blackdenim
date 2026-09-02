package com.example.habittracker.model;

import java.util.ArrayList;
import java.util.List;

public class MockUserDAO implements IUserDAO{
    private final List<User> users = new ArrayList<>();

    // Searches the users list for users with a specific username
    // TODO: fix comments
    @Override
    public User findByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    // Checks if a specific username exists in the list
    // TODO: fix comments
    @Override
    public boolean usernameExists(String username) {
        return findByUsername(username) != null;
    }

    // TODO: add comments
    @Override
    public void addUser(User user) {
        users.add(user);
    }

}
