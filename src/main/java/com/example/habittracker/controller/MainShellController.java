package com.example.habittracker.controller;

import com.example.habittracker.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.List;

public class MainShellController {

    private static final String VIEW_FOLDER = "/com/example/habittracker/";
    private static final String HOME_VIEW = "todayhabit.fxml";
    private static final String HABITS_VIEW = "habits.fxml";
    private static final String ACTIVE_STYLE = "active";

    @FXML
    private StackPane contentArea;

    @FXML
    private Button homeButton;

    @FXML
    private Button habitsButton;

    @FXML
    private Button communityGoalsButton;

    @FXML
    private Button leaderboardButton;

    @FXML
    private Button achievementsButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button settingsButton;

    private List<Button> navButtons;

    private User user;

    @FXML
    public void initialize() {
        navButtons = List.of(homeButton, habitsButton, communityGoalsButton, leaderboardButton,
                achievementsButton, profileButton, settingsButton);
    }

    public void setUser(User user) {
        this.user = user;
        navigate(homeButton, HOME_VIEW);
    }

    @FXML
    private void onHomeClicked() {
        navigate(homeButton, HOME_VIEW);
    }

    @FXML
    private void onHabitsClicked() {
        navigate(habitsButton, HABITS_VIEW);
    }

    @FXML
    private void onCommunityGoalsClicked() {
        showComingSoon(communityGoalsButton, "Community goal page");
    }

    @FXML
    private void onLeaderboardClicked() {
        showComingSoon(leaderboardButton, "Leaderboard page");
    }

    @FXML
    private void onAchievementsClicked() {
        showComingSoon(achievementsButton, "Achievement page");
    }

    @FXML
    private void onProfileClicked() {
        showComingSoon(profileButton, "Profile page");
    }

    @FXML
    private void onSettingsClicked() {
        showComingSoon(settingsButton, "Setting page");
    }

    private void navigate(Button navButton, String view) {
        setActive(navButton);
        loadPage(view);
    }

    private void showComingSoon(Button navButton, String pageName) {
        setActive(navButton);
        contentArea.getChildren().setAll(new Label(pageName + " is coming soon!"));
    }

    /**
     * Highlights the given sidebar button and removes the highlight from the others.
     */
    private void setActive(Button selected) {
        for (Button button : navButtons) {
            button.getStyleClass().remove(ACTIVE_STYLE);
        }
        selected.getStyleClass().add(ACTIVE_STYLE);
    }

    private void loadPage(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(VIEW_FOLDER + path)
            );
            Node page = loader.load();

            Object controller = loader.getController();
            if (controller instanceof TodayHabitController) {
                TodayHabitController homeController = (TodayHabitController) controller;
                homeController.setOnAddHabit(() -> navigate(habitsButton, HABITS_VIEW));
                homeController.setUser(user);
            } else if (controller instanceof HabitsController) {
                ((HabitsController) controller).setUser(user);
            }

            contentArea.getChildren().setAll(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
