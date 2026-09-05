package com.example.habittracker.model;


public class AuthenticationService implements IAuthenticationService {

    private final IUserDAO userDAO;

    public AuthenticationService(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Creates a new user if username and password are valid and adds it to the userDAO.
     * @param username The username of the user.
     * @param password The password of the user.
     */
    public void register (String username, String password) {
        validateUsername(username);
        validatePassword(password);

        User user = new User(username, password);
        userDAO.addUser(user);
    }

    /**
     * Returns the user from UserDAO which matches the username and password entered.
     * @param username The username of the user.
     * @param password The password of the user.
     */
    public User login (String username, String password) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be blank. Please enter a username");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be blank. Please enter a password");
        }

        User user = userDAO.findByUsername(username);

        if (user == null) {
            throw new IllegalArgumentException();
        }
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException();
        }

        return user;
    }

    /**
     * Checks that a username is of a valid format for registration: Username must not be null or blank,
     * cannot contain spaces, and must not be the same as another user.
     * @param username The username to be validated.
     */
    private void validateUsername(String username){

        if (username == null ||username.isBlank()){
            throw new IllegalArgumentException("Username cannot be blank.");
        }
        if (username.length() > 20 ){
            throw new IllegalArgumentException("Username must be 20 characters or less.");
        }
        if (username.contains(" ")) {
            throw new IllegalArgumentException("Password cannot contain spaces.");
        }
        if (userDAO.usernameExists(username)){
            throw new IllegalArgumentException("Username is already taken.");
        }

    }

    /**
     * Checks that a password is of a valid format for registration: Password must not be null or blank,
     * cannot contain spaces, must be 8-20 characters, must contain at least one lowercase letter,
     * uppercase letter, and number.
     * @param password The password to be validated.
     */
    private static void validatePassword(String password){
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be blank");
        }
        if (password.contains(" ")) {
            throw new IllegalArgumentException("Password cannot contain spaces.");
        }
        if (password.length() < 8 || password.length() > 20) {
            throw new IllegalArgumentException("Password must be between 8 and 20 characters");
        }

        // TODO POSSIBLY integrate special character check

        boolean hasDigit = false;
        boolean hasUppercase = false;
        boolean hasLowercase = false;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(ch)) {
                hasLowercase = true;
            }
        }

        if (!hasDigit) {
            throw new IllegalArgumentException("Password must contain at least one number .");
        }
        if (!hasUppercase){
            throw new IllegalArgumentException("Password must contain an uppercase character.");
        }
        if (!hasLowercase){
            throw new IllegalArgumentException("Password must contain a lower case character.");
        }
    }
}
