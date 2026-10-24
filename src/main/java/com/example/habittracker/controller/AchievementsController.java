package com.example.habittracker.controller;

import com.example.habittracker.model.Achievement;
import com.example.habittracker.model.AchievementService;
import com.example.habittracker.model.SqliteHabitEntryDAO;
import com.example.habittracker.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;

import java.util.List;

public class AchievementsController {

    @FXML
    private Label summaryLabel;
    @FXML
    private VBox badgeContainer;

    private final AchievementService achievementService;

    public AchievementsController() {
        this(new AchievementService(new SqliteHabitEntryDAO()));
    }

    public AchievementsController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    /**
     * Loads the logged-in user's achievements and draws a card for each one.
     */
    public void setUser(User user) {
        List<Achievement> achievements = achievementService.getAchievementsForUser(user.getId());

        long unlocked = achievements.stream().filter(Achievement::isUnlocked).count();
        summaryLabel.setText(unlocked + " of " + achievements.size() + " achievements unlocked");

        badgeContainer.getChildren().clear();
        for (Achievement achievement : achievements) {
            badgeContainer.getChildren().add(createCard(achievement));
        }
    }

    private VBox createCard(Achievement achievement) {
        VBox card = new VBox(6);
        card.getStyleClass().add("card");
        card.getStyleClass().add(achievement.isUnlocked() ? "badge-unlocked" : "badge-locked");

        Label name = new Label(achievement.getName());
        name.getStyleClass().add("card-title");

        Label description = new Label(achievement.getDescription());
        description.getStyleClass().add("habit-detail");

        ProgressBar bar = new ProgressBar((double) achievement.getProgress() / achievement.getTarget());
        bar.setMaxWidth(Double.MAX_VALUE);

        String status = achievement.isUnlocked() ? "Unlocked" : "Locked";
        Label progress = new Label(status + "  -  " + achievement.getProgress() + " / " + achievement.getTarget());
        progress.getStyleClass().add("habit-detail");

        card.getChildren().addAll(name, description, bar, progress);
        return card;
    }
}