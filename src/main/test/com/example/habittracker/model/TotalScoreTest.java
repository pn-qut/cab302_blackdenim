package com.example.habittracker.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TotalScoreTest {
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

    @Test
    public void TotalScoreIsZeroWhenNothingCompleted() {
        assertEquals(0, habitService.getUserTotalScore(USER_ID));
    }

    @Test
    public void EachCompletedHabitIsWorthOnePoint() {
        habitService.setHabitCompleted(USER_ID, drinkWater.getId(), TODAY, true);
        habitService.setHabitCompleted(USER_ID, exercise.getId(), TODAY, true);

        assertEquals(2, habitService.getUserTotalScore(USER_ID));
    }

    @Test
    public void TotalScoreIncludesEveryMonth() {
        habitService.setHabitCompleted(USER_ID, drinkWater.getId(), TODAY, true);
        habitService.setHabitCompleted(USER_ID, drinkWater.getId(), TODAY.minusMonths(1), true);
        habitService.setHabitCompleted(USER_ID, drinkWater.getId(), TODAY.minusYears(1), true);

        assertEquals(3, habitService.getUserTotalScore(USER_ID));
    }

    @Test
    public void UntickingAHabitRemovesItsPoint() {
        habitService.setHabitCompleted(USER_ID, drinkWater.getId(), TODAY, true);
        habitService.setHabitCompleted(USER_ID, drinkWater.getId(), TODAY, false);

        assertEquals(0, habitService.getUserTotalScore(USER_ID));
    }

    @Test
    public void OtherUsersPointsDoNotCount() {
        habitService.setHabitCompleted(OTHER_USER_ID, drinkWater.getId(), TODAY, true);

        assertEquals(0, habitService.getUserTotalScore(USER_ID));
    }
}
