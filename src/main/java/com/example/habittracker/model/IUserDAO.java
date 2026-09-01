package com.example.habittracker.model;

/**
 * Interface for the User Data Access Object that handles
 * the operations for the User class with the database.
 */
public interface IUserDAO {

    /**
     * Retrieves a user from the database, searching by username.
     * @param username The username of the user to be retrieved.
     */
    public User findByUsername(String username);

    /**
     * Checks if a username exists in the database.
     * @param username The username to be searched for in the database.
     */
    public boolean usernameExists(String username);

    /**
     * Adds a new user to the database.
     * @param user The user to add.
     */
    public void addUser(User user);

}
