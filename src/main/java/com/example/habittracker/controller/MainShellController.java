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
/**
 * Controller for the Main Menu sidebar
 */
public class MainShellController {

    private static final String VIEW_FOLDER = "/com/example/habittracker/";
    private static final String HOME_VIEW = "todayhabit.fxml";
    private static final String HABITS_VIEW = "habits.fxml";
    private static final String PROFILE_VIEW = "profile.fxml";
    private static final String LEADERBOARD_VIEW = "leaderboard.fxml";
    private static final String ACHIEVEMENTS_VIEW = "achievements.fxml";
    private static final String COMMUNITYGOALS_VIEW = "communitygoals.fxml";
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

    private List<Button> navButtons;

    private User user;

    private Runnable onLogout;

    @FXML
    public void initialize() {
        navButtons = List.of(homeButton, habitsButton, communityGoalsButton, leaderboardButton,
                achievementsButton, profileButton);
    }

    public void setUser(User user) {
        this.user = user;
        navigate(homeButton, HOME_VIEW);
    }
    
    public void setOnLogout(Runnable onLogout) {
    this.onLogout = onLogout;
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
        navigate(communityGoalsButton, COMMUNITYGOALS_VIEW);
    }

    @FXML
    private void onLeaderboardClicked() {
        navigate(leaderboardButton, LEADERBOARD_VIEW);
    }

    @FXML
    private void onAchievementsClicked() {
        navigate(achievementsButton, ACHIEVEMENTS_VIEW);
    }

    @FXML
    private void onProfileClicked() {
        navigate(profileButton, PROFILE_VIEW);
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
            } else if (controller instanceof ProfileController) {
                ProfileController profileController = (ProfileController) controller;
                profileController.setUser(user);
                profileController.setOnLogout(onLogout);
            } else if (controller instanceof AchievementsController) {
                ((AchievementsController) controller).setUser(user);
            } else if (controller instanceof CommunityGoalsController) {
                CommunityGoalsController profileController = (CommunityGoalsController) controller;
                profileController.setUser(user);
            }

            contentArea.getChildren().setAll(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
