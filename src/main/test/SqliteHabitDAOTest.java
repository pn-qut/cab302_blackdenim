package com.example.habittracker.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SqliteHabitDAOTest {

    private SqliteHabitDAO habitDAO;

    @BeforeEach
    void setUp() {
        habitDAO = new SqliteHabitDAO();
    }

    @Test
    void findHabitByIdReturnsMatchingHabit() {
        Habit first = habitDAO.getAllHabits().get(0);

        Habit found = habitDAO.findHabitById(first.getId());

        assertNotNull(found);
        assertEquals(first.getName(), found.getName());
    }

    @Test
    void findHabitByIdReturnsNullForUnknownId() {
        assertNull(habitDAO.findHabitById(-1));
    }
}