package com.example.habittracker.model;

public class User {
    private int id;
    private String username;
    private String password;
    private int postcode;

    public User(String username, String password, int postcode) {
        this.username = username;
        this.password = password;
        this.postcode = postcode;
    }

    public int getId() {
        return id;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the username of the user.
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the password of the user.
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}

