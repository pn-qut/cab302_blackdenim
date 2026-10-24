package com.example.habittracker.model;

import java.util.ArrayList;
import java.util.List;

public class AchievementService {
    private final IHabitEntryDAO habitEntryDAO;

    public AchievementService(IHabitEntryDAO habitEntryDAO) {
        this.habitEntryDAO = habitEntryDAO;
    }

    public List<Achievement> getAchievementsForUser(int userId) {
        return new ArrayList<>();
    }

    public List<Achievement> getAchievements(int totalLogs) {
        List<Achievement> list = new ArrayList<>();
        list.add(new Achievement("First Step", "Log your first habit", 1, totalLogs));
        list.add(new Achievement("Getting Going", "Log 10 habits", 10, totalLogs));
        list.add(new Achievement("Eco Champion", "Log 50 habits", 50, totalLogs));
        return list;
    }
}