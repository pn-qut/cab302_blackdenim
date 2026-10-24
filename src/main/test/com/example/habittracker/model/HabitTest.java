package com.example.habittracker.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HabitTest {

    @Test
    public void SettingTheNameChangesTheName() {
        Habit habit = new Habit(1, "Drink water");

        habit.setName("Exercise");

        assertEquals("Exercise", habit.getName());
    }
}
