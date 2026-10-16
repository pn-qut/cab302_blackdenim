package com.example.habittracker.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HabitServiceTest {
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

    @Test
    public void NewUserHasNoHabits() {
        assertTrue(habitService.getUserHabits(USER_ID).isEmpty());
    }

    @Test
    public void GetUserHabitsReturnsOnlyThatUsersHabits() {
        habitService.addHabitToUser(USER_ID, drinkWater.getId());
        habitService.addHabitToUser(OTHER_USER_ID, exercise.getId());

        List<Habit> habits = habitService.getUserHabits(USER_ID);

        assertEquals(1, habits.size());
        assertEquals("Drink water", habits.get(0).getName());
    }

    @Test
    public void RemovedHabitIsNoLongerReturned() {
        habitService.addHabitToUser(USER_ID, drinkWater.getId());
        habitService.removeHabitFromUser(USER_ID, drinkWater.getId());

        assertTrue(habitService.getUserHabits(USER_ID).isEmpty());
    }

    @Test
    public void HabitIsNotCompletedByDefault() {
        assertFalse(habitService.isHabitCompleted(USER_ID, drinkWater.getId(), TODAY));
    }

    @Test
    public void CompletedHabitIsReportedAsCompleted() {
        habitService.setHabitCompleted(USER_ID, drinkWater.getId(), TODAY, true);

        assertTrue(habitService.isHabitCompleted(USER_ID, drinkWater.getId(), TODAY));
    }

    @Test
    public void UncompletedHabitIsNoLongerCompleted() {
        habitService.setHabitCompleted(USER_ID, drinkWater.getId(), TODAY, true);
        habitService.setHabitCompleted(USER_ID, drinkWater.getId(), TODAY, false);

        assertFalse(habitService.isHabitCompleted(USER_ID, drinkWater.getId(), TODAY));
    }

    @Test
    public void CompletionOnlyAppliesToThatDate() {
        habitService.setHabitCompleted(USER_ID, drinkWater.getId(), TODAY, true);

        assertFalse(habitService.isHabitCompleted(USER_ID, drinkWater.getId(), TODAY.minusDays(1)));
    }

    @Test
    public void CompletionOnlyAppliesToThatUser() {
        habitService.setHabitCompleted(USER_ID, drinkWater.getId(), TODAY, true);

        assertFalse(habitService.isHabitCompleted(OTHER_USER_ID, drinkWater.getId(), TODAY));
    }
}
