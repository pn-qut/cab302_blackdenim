package com.example.habittracker.controller;

import com.example.habittracker.model.AuthenticationService;
import com.example.habittracker.model.IAuthenticationService;
import com.example.habittracker.model.SqliteUserDAO;
import com.example.habittracker.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.util.Optional;
/**
 * Controller for the User Profile page
 */
public class ProfileController {

    @FXML private Label usernameLabel;
    @FXML private PasswordField currentPasswordField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label messageLabel;

    private final IAuthenticationService authenticationService;
    private User user;
    private Runnable onLogout;

    public ProfileController() {
        this.authenticationService = new AuthenticationService(new SqliteUserDAO());
    }

    public void setUser(User user) {
        this.user = user;
        usernameLabel.setText(user.getUsername());
    }

    public void setOnLogout(Runnable onLogout) {
        this.onLogout = onLogout;
    }

    @FXML
    private void onChangePasswordClicked() {
        String current = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirm = confirmPasswordField.getText();

        if (!newPassword.equals(confirm)) {
            showMessage("New passwords do not match.", true);
            return;
        }
        try {
            authenticationService.changePassword(user.getUsername(), current, newPassword);
            currentPasswordField.clear();
            newPasswordField.clear();
            confirmPasswordField.clear();
            showMessage("Password changed.", false);
        } catch (IllegalArgumentException e) {
            showMessage(e.getMessage(), true);
        }
    }

    @FXML
    private void onLogoutClicked() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to log out?");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK && onLogout != null) {
            onLogout.run();
        }
    }

    private void showMessage(String text, boolean isError) {
        messageLabel.setText(text);
        messageLabel.setStyle(isError ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
    }
}