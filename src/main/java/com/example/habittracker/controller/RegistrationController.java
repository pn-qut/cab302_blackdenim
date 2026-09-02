package com.example.habittracker.controller;

import com.example.habittracker.model.AuthenticationService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class RegistrationController {

    private AuthenticationService authenticationService;

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;

    @FXML
    private Label errorLabel;

    public RegistrationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @FXML
    public void onRegisterConfirmButtonClicked() {
        try {
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();
            authenticationService.register(username, password);

            // Registration succeeded
            errorLabel.setText("");

            // TODO: implement functionality after registration. e.g. go to login screen??
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
        catch (Exception e){
            errorLabel.setText("error");  // TODO: change error message
            // TODO: Display an error message to the user in the UI
            // TODO: add exception catch for all types of exceptions
        }
    }

    @FXML
    public void onLoginButtonClicked() {
        // TODO: Return to login page
    }
}
