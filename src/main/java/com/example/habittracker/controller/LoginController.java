package com.example.habittracker.controller;

import com.example.habittracker.model.AuthenticationService;
import com.example.habittracker.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {

    private AuthenticationService authenticationService;

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label errorLabel;

    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @FXML
    public void onLoginConfirmButtonClicked() {
        try {
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();
            User user = authenticationService.login(username, password);

            // Login succeeded
            errorLabel.setText("");

            // TODO: functionality for what happens after logging in
        } catch (Exception e){
            errorLabel.setText("Incorrect username or password.");
            // TODO: Display an error message to the user in the UI
        }
    }

    @FXML
    public void onGoToRegistrationButtonClicked() {
        // TODO: Go to registration page
    }
}
