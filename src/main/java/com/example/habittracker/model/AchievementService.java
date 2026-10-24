package com.example.habittracker.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AchievementService {
    private static final LocalDate EARLIEST_DATE = LocalDate.of(2000, 1, 1);

    private static final int FIRST_STEP_TARGET = 1;
    private static final int GETTING_GOING_TARGET = 10;
    private static final int ECO_CHAMPION_TARGET = 50;

    private final IHabitEntryDAO habitEntryDAO;

    public AchievementService(IHabitEntryDAO habitEntryDAO) {
        this.habitEntryDAO = habitEntryDAO;
    }

    /**
     * Returns the achievements for a user, based on their total completed habits.
     */
    public List<Achievement> getAchievementsForUser(int userId) {
        return getAchievements(countTotalCompletions(userId));
    }

    /**
     * Returns the achievements and their progress for a given number of completed habits.
     */
    public List<Achievement> getAchievements(int totalLogs) {
        List<Achievement> list = new ArrayList<>();
        list.add(new Achievement("First Step", "Log your first habit", FIRST_STEP_TARGET, totalLogs));
        list.add(new Achievement("Getting Going", "Log " + GETTING_GOING_TARGET + " habits", GETTING_GOING_TARGET, totalLogs));
        list.add(new Achievement("Eco Champion", "Log " + ECO_CHAMPION_TARGET + " habits", ECO_CHAMPION_TARGET, totalLogs));
        return list;
    }

    /**
     * Adds up every habit completion a user has recorded, across all habits.
     */
    private int countTotalCompletions(int userId) {
        Map<Integer, Integer> counts = habitEntryDAO.countCompletionsByHabit(
                userId, EARLIEST_DATE, LocalDate.now());
        int total = 0;
        for (int completions : counts.values()) {
            total += completions;
        }
        return total;
    }
}