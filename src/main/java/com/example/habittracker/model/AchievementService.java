package com.example.habittracker.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AchievementService {
    private final IHabitEntryDAO habitEntryDAO;

    public AchievementService(IHabitEntryDAO habitEntryDAO) {
        this.habitEntryDAO = habitEntryDAO;
    }

    /**
     * Returns the achievements for a user, based on their total completed habits.
     */
    public List<Achievement> getAchievementsForUser(int userId) {
        Map<Integer, Integer> counts = habitEntryDAO.countCompletionsByHabit(
                userId, LocalDate.of(2000, 1, 1), LocalDate.now());
        int total = 0;
        for (int completions : counts.values()) {
            total += completions;
        }
        return getAchievements(total);
    }

    /**
     * Returns the achievements and their progress for a given number of completed habits.
     */
    public List<Achievement> getAchievements(int totalLogs) {
        List<Achievement> list = new ArrayList<>();
        list.add(new Achievement("First Step", "Log your first habit", 1, totalLogs));
        list.add(new Achievement("Getting Going", "Log 10 habits", 10, totalLogs));
        list.add(new Achievement("Eco Champion", "Log 50 habits", 50, totalLogs));
        return list;
    }
}