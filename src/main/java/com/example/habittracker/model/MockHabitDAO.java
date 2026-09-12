package com.example.habittracker.model;

import java.util.ArrayList;
import java.util.List;

public class MockHabitDAO implements IHabitDAO{
    private final List<Habit> defaultHabits = new ArrayList<>(List.of(
            new Habit(1, "Habit 1"),
            new Habit(2, "Habit 2")
    ));

    @Override
    public List<Habit> getAllHabits() {
        return defaultHabits;
    }

    @Override
    public Habit findHabitById(int id) {
        return defaultHabits.stream()
                .filter(habit -> habit.getId() == id)
                .findFirst()
                .orElse(null);
    }



}
