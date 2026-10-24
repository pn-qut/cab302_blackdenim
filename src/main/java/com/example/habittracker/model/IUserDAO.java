package com.example.habittracker.model;

import java.util.List;

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
     * Updates the stored password for a user.
     * @param username The user whose password is changing.
     * @param newPassword The new password to store.
     */   
    public void updatePassword(String username, String newPassword);

    /**
     * Retrieves the suburbs associated with a postcode.
     * @param postcode The postcode to search for.
     */
    public List<String> findSuburbsByPostcode(int postcode);


    /**
     * Adds a new user to the database.
     * @param user The user to add.
     */
    public void addUser(User user);

}
