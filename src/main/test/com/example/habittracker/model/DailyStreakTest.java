package com.example.habittracker.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DailyStreakTest {
    private static final int USER_ID = 1;
    private static final int OTHER_USER_ID = 2;
    private static final LocalDate TODAY = LocalDate.of(2026, 10, 24);

    private final Habit drinkWater = new Habit(1, "Drink water");
    private final Habit exercise = new Habit(2, "Exercise");

    private HabitService habitService;

    @BeforeEach
    public void setUp() {
        IHabitEntryDAO entryDAO = new MockHabitEntryDAO();
        IUserHabitDAO userHabitDAO = new MockUserHabitDAO(List.of(drinkWater, exercise));
        habitService = new HabitService(entryDAO, userHabitDAO);
    }

    private void complete(Habit habit, LocalDate date) {
        habitService.setHabitCompleted(USER_ID, habit.getId(), date, true);
    }

    @Test
    public void StreakIsZeroWhenNothingCompleted() {
        assertEquals(0, habitService.getDailyStreak(USER_ID, TODAY));
    }

    @Test
    public void OneHabitCompletedTodayGivesStreakOfOne() {
        complete(drinkWater, TODAY);

        assertEquals(1, habitService.getDailyStreak(USER_ID, TODAY));
    }

    @Test
    public void SeveralHabitsOnTheSameDayStillCountAsOneDay() {
        complete(drinkWater, TODAY);
        complete(exercise, TODAY);

        assertEquals(1, habitService.getDailyStreak(USER_ID, TODAY));
    }

    @Test
    public void ConsecutiveDaysAreCounted() {
        complete(drinkWater, TODAY);
        complete(exercise, TODAY.minusDays(1));
        complete(drinkWater, TODAY.minusDays(2));

        assertEquals(3, habitService.getDailyStreak(USER_ID, TODAY));
    }

    @Test
    public void StreakIsKeptUntilTodayIsOver() {
        complete(drinkWater, TODAY.minusDays(1));
        complete(drinkWater, TODAY.minusDays(2));

        assertEquals(2, habitService.getDailyStreak(USER_ID, TODAY));
    }

    @Test
    public void MissedDayBreaksTheStreak() {
        complete(drinkWater, TODAY);
        complete(drinkWater, TODAY.minusDays(1));
        complete(drinkWater, TODAY.minusDays(3));
        complete(drinkWater, TODAY.minusDays(4));

        assertEquals(2, habitService.getDailyStreak(USER_ID, TODAY));
    }

    @Test
    public void StreakIsLostWhenYesterdayWasMissed() {
        complete(drinkWater, TODAY.minusDays(2));

        assertEquals(0, habitService.getDailyStreak(USER_ID, TODAY));
    }

    @Test
    public void UntickingTodaysOnlyHabitFallsBackToYesterdaysStreak() {
        complete(drinkWater, TODAY.minusDays(1));
        complete(drinkWater, TODAY);
        habitService.setHabitCompleted(USER_ID, drinkWater.getId(), TODAY, false);

        assertEquals(1, habitService.getDailyStreak(USER_ID, TODAY));
    }

    @Test
    public void OtherUsersCompletionsDoNotCount() {
        habitService.setHabitCompleted(OTHER_USER_ID, drinkWater.getId(), TODAY, true);

        assertEquals(0, habitService.getDailyStreak(USER_ID, TODAY));
    }
}
