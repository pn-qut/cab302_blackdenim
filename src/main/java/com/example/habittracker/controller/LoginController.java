package com.example.habittracker.controller;

import com.example.habittracker.LoginUI;
import com.example.habittracker.MainPageApp;
import com.example.habittracker.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    private IUserDAO userDAO;

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
        userDAO = new SqliteUserDAO();
        this.authenticationService = new AuthenticationService(userDAO);
    }

    @FXML
    public void onLoginConfirmButtonClicked() {
        try {
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();
            User user = authenticationService.login(username, password);

            goToMainWindow(user);
        } catch (Exception e){
            errorLabel.setText("Incorrect username or password.");
        }
    }

    /**
     * Swaps the current stage's scene to the main application window.
     */
    private void goToMainWindow(User user) {
        try {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            MainPageApp.showOn(stage, user);
        } catch (IOException e) {
            errorLabel.setText("Unable to load the main window.");
        }
    }

    @FXML
    public void onGoToRegistrationButtonClicked() throws IOException {
        Stage stage = (Stage) registerLink.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(LoginUI.class.getResource("registration-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), LoginUI.WIDTH, LoginUI.HEIGHT);
        stage.setScene(scene);
    }
}
