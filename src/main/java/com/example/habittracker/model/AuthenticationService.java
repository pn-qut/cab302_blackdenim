package com.example.habittracker.model;


public class AuthenticationService implements IAuthenticationService {

    private final IUserDAO userDAO;

    public AuthenticationService(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // TODO: add comments
    public void register (String username, String password) {
        validateUsername(username);
        validatePassword(password);

        User user = new User(username, password);
        userDAO.addUser(user);
    }

    // TODO: add comments
    public User login (String username, String password) {
        User user = userDAO.findByUsername(username);

        if (user == null) {
            return null;
            // TODO: throw exception
        }

        if (!user.getPassword().equals(password)) {
            return null;
            // TODO: throw exception
        }

        return user;
    }

    // TODO: add comments
    private void validateUsername(String username){

        if (userDAO.usernameExists(username)){
            throw new IllegalArgumentException("Username is already taken.");
        }

        // TODO: throw exceptions e.g. username is null

    }

    // TODO: add comments
    private void validatePassword(String password){
        // TODO: throw exceptions that will be displayed to the user saying the password is incorrect format
        // e.g. min password length, must contain capital letter etc.
    }

}
