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
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }
        // TODO: throw exceptions that will be displayed to the user saying the password is incorrect format
        if (password.contains(" ")) {
            throw new IllegalArgumentException("Password cannot contain spaces.");
        }
        if (password.length() > 20) {
            throw new IllegalArgumentException("Password must be less than 20 characters.");
        }
        // TODO POSSIBLY integrate special character check


        char ch;
        boolean hasUppercase = false;
        ch = password.charAt(0);
        if (!(Character.isUpperCase(ch))){
            for (int i=0; i < password.length(); i++){
                if (Character.isUpperCase(password.charAt(i))){
                    hasUppercase = true;
                    break;
                }
            }
            if (!hasUppercase){
                throw new IllegalArgumentException("Password must contain an uppercase character.");
            }

        }
        boolean hasLowercase = false;
        if (!(Character.isLowerCase(password.charAt(0)))) {
            for (int i=0; i < password.length(); i++){
                if (Character.isLowerCase(password.charAt(i))){
                    hasLowercase = true;
                    break;
                }
            }
            if (!hasLowercase){
                throw new IllegalArgumentException("Password must contain a lower case character.");
            }
            }

    }

}
