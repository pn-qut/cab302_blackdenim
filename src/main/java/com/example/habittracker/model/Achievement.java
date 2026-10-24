package com.example.habittracker.model;

public class Achievement {
    private final String name;
    private final String description;
    private final int target;
    private final int progress;

    public Achievement(String name, String description, int target, int progress) {
        this.name = name;
        this.description = description;
        this.target = target;
        this.progress = progress;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getTarget() { return target; }
    public int getProgress() { return Math.min(progress, target); }
    public boolean isUnlocked() { return progress >= target; }
}