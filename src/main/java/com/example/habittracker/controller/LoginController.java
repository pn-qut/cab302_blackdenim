package com.example.habittracker.controller;

import com.example.habittracker.LoginUI;
import com.example.habittracker.model.AuthenticationService;
import com.example.habittracker.model.MockUserDAO;
import com.example.habittracker.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    private AuthenticationService authenticationService;

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private Button loginButton;
    @FXML
    private Hyperlink registerLink;

    public LoginController() {
        this.authenticationService = new AuthenticationService(new MockUserDAO());
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
        }
    }

    @FXML
    public void onGoToRegistrationButtonClicked() throws IOException {
        Stage stage = (Stage) registerLink.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(LoginUI.class.getResource("registration-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), LoginUI.WIDTH, LoginUI.HEIGHT);
        stage.setScene(scene);
        // TODO: Go to registration page
    }
}
