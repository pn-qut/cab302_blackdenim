package com.example.habittracker.model;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class LastDoneTest {
    private static final int USER_ID = 1;
    private static final int OTHER_USER_ID = 2;
    private static final LocalDate TODAY = LocalDate.of(2026, 10, 3);

    private final Habit drinkWater = new Habit(1, "Drink water");
    private final Habit exercise = new Habit(2, "Exercise");

    private HabitService habitService;

    @BeforeEach
    public void setUp() {
        IHabitEntryDAO entryDAO = new MockHabitEntryDAO();
        IUserHabitDAO userHabitDAO = new MockUserHabitDAO(List.of(drinkWater, exercise));
        habitService = new HabitService(entryDAO, userHabitDAO);
    }

    private void complete(Habit habit, LocalDate... dates) {
        for (LocalDate date : dates) {
            habitService.setHabitCompleted(USER_ID, habit.getId(), date, true);
        }
    }

    @Test
    public void NeverCompletedHabitHasNoLastDate() {
        assertNull(habitService.getLastCompletionDate(USER_ID, drinkWater.getId()));
    }

    @Test
    public void LastDateIsTheMostRecentCompletion() {
        complete(drinkWater, TODAY.minusDays(5), TODAY.minusDays(1), TODAY.minusDays(3));

        assertEquals(TODAY.minusDays(1), habitService.getLastCompletionDate(USER_ID, drinkWater.getId()));
    }

    @Test
    public void UntickingTodayGoesBackToPreviousDate() {
        complete(drinkWater, TODAY.minusDays(2), TODAY);
        habitService.setHabitCompleted(USER_ID, drinkWater.getId(), TODAY, false);

        assertEquals(TODAY.minusDays(2), habitService.getLastCompletionDate(USER_ID, drinkWater.getId()));
    }

    @Test
    public void LastDateIgnoresOtherHabits() {
        complete(exercise, TODAY);

        assertNull(habitService.getLastCompletionDate(USER_ID, drinkWater.getId()));
    }

    @Test
    public void LastDateIgnoresOtherUsers() {
        habitService.setHabitCompleted(OTHER_USER_ID, drinkWater.getId(), TODAY, true);

        assertNull(habitService.getLastCompletionDate(USER_ID, drinkWater.getId()));
    }

    @Test
    public void TextForNeverDone() {
        assertEquals("Not done yet", LastDoneText.describe(null, TODAY));
    }

    @Test
    public void TextForDoneToday() {
        assertEquals("Done today", LastDoneText.describe(TODAY, TODAY));
    }

    @Test
    public void TextForDoneYesterday() {
        assertEquals("Last done yesterday", LastDoneText.describe(TODAY.minusDays(1), TODAY));
    }

    @Test
    public void TextForOlderDateShowsDayAndMonth() {
        assertEquals("Last done 1 Oct", LastDoneText.describe(LocalDate.of(2026, 10, 1), TODAY));
    }

    @Test
    public void TextForDateInEarlierMonth() {
        assertEquals("Last done 28 Sep", LastDoneText.describe(LocalDate.of(2026, 9, 28), TODAY));
    }
}
