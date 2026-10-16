package com.example.habittracker.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeeklyProgressTest {
    private static final int USER_ID = 1;
    private static final int OTHER_USER_ID = 2;
    private static final LocalDate MONDAY = LocalDate.of(2026, 9, 28);
    private static final LocalDate TODAY = LocalDate.of(2026, 10, 3); // Saturday

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

    private int firstHabitDaysThisWeek(LocalDate today) {
        return habitService.getWeeklyProgress(USER_ID, today).get(0).getCompletedDays();
    }

    @Test
    public void NoHabitsGivesNoProgress() {
        assertTrue(habitService.getWeeklyProgress(USER_ID, TODAY).isEmpty());
    }

    @Test
    public void HabitWithNoCompletionsHasZeroDays() {
        habitService.addHabitToUser(USER_ID, drinkWater.getId());

        HabitProgress progress = habitService.getWeeklyProgress(USER_ID, TODAY).get(0);

        assertEquals("Drink water", progress.getHabit().getName());
        assertEquals(0, progress.getCompletedDays());
        assertEquals(7, progress.getTargetDays());
    }

    @Test
    public void CountsCompletionsFromMondayToToday() {
        habitService.addHabitToUser(USER_ID, drinkWater.getId());
        complete(drinkWater, MONDAY, MONDAY.plusDays(2), TODAY);

        assertEquals(3, firstHabitDaysThisWeek(TODAY));
    }

    @Test
    public void IgnoresCompletionsBeforeMonday() {
        habitService.addHabitToUser(USER_ID, drinkWater.getId());
        complete(drinkWater, MONDAY.minusDays(1));

        assertEquals(0, firstHabitDaysThisWeek(TODAY));
    }

    @Test
    public void WeekStartsOnMondayWhenTodayIsMonday() {
        habitService.addHabitToUser(USER_ID, drinkWater.getId());
        complete(drinkWater, MONDAY, MONDAY.minusDays(1));

        assertEquals(1, firstHabitDaysThisWeek(MONDAY));
    }

    @Test
    public void EachHabitIsCountedSeparately() {
        habitService.addHabitToUser(USER_ID, drinkWater.getId());
        habitService.addHabitToUser(USER_ID, exercise.getId());
        complete(drinkWater, MONDAY, TODAY);
        complete(exercise, TODAY);

        List<HabitProgress> progress = habitService.getWeeklyProgress(USER_ID, TODAY);

        assertEquals(2, progress.get(0).getCompletedDays());
        assertEquals(1, progress.get(1).getCompletedDays());
    }

    @Test
    public void IgnoresOtherUsersCompletions() {
        habitService.addHabitToUser(USER_ID, drinkWater.getId());
        habitService.setHabitCompleted(OTHER_USER_ID, drinkWater.getId(), TODAY, true);

        assertEquals(0, firstHabitDaysThisWeek(TODAY));
    }

    @Test
    public void ProgressIsFractionOfTargetDays() {
        HabitProgress everyDay = new HabitProgress(drinkWater, 7, 7);
        HabitProgress notStarted = new HabitProgress(exercise, 0, 7);

        assertEquals(1.0, everyDay.getProgress());
        assertEquals(0.0, notStarted.getProgress());
    }
}
