package com.example.habittracker.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ScottController {

    @FXML
    private Rectangle mouth;

    private final Timeline talkingAnimation = new Timeline(
            new KeyFrame(Duration.ZERO, event -> mouth.setVisible(true)),
            new KeyFrame(Duration.seconds(0.2), event -> mouth.setVisible(false)),
            new KeyFrame(Duration.seconds(0.4), event -> mouth.setVisible(true))
    );

    @FXML
    public void initialize() {
        talkingAnimation.setCycleCount(Animation.INDEFINITE);
        talkingAnimation.play();
    }
}
