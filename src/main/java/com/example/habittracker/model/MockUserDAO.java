package com.example.habittracker.model;

import java.util.ArrayList;
import java.util.List;

public class MockUserDAO implements IUserDAO{
    private final List<User> users = new ArrayList<>();

    /**
     * Retrieves a user from the user list, searching by username.
     * @param username The username of the user to be retrieved.
     */
    @Override
    public User findByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * Checks if a username exists in the users list.
     * @param username The username to be searched for in the isers list.
     */
    @Override
    public boolean usernameExists(String username) {
        return findByUsername(username) != null;
    }

    /**
     * Adds a new user to the users list.
     * @param user The user to add to the users list.
     */
    @Override
    public void addUser(User user) {
        users.add(user);
    }

}
