package com.example.habittracker.controller;

import com.example.habittracker.LoginUI;
import com.example.habittracker.model.AuthenticationService;
import com.example.habittracker.model.IUserDAO;
import com.example.habittracker.model.SqliteUserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;


public class RegistrationController {

    private IUserDAO userDAO;

    private AuthenticationService authenticationService;

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button registerConfirmButton;
    @FXML
    private Hyperlink loginLink;
    @FXML
    private Label errorLabel;

    public RegistrationController() {
        userDAO = new SqliteUserDAO();
        this.authenticationService = new AuthenticationService(userDAO);
    }

    @FXML
    public void onRegisterConfirmButtonClicked() {
        try {
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();
            authenticationService.register(username, password);

            // Registration succeeded
            errorLabel.setText("");

            onGoToLoginButtonClicked();
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
        catch (Exception e){
            errorLabel.setText("Unable to register. Please try again.");
        }
    }

    @FXML
    public void onGoToLoginButtonClicked() throws IOException {
        Stage stage = (Stage) loginLink.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(LoginUI.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), LoginUI.WIDTH, LoginUI.HEIGHT);
        stage.setScene(scene);
    }
}
