package com.example.habittracker.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SqliteHabitDAOTest {

    private Connection connection;
    private SqliteHabitDAO habitDAO;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        habitDAO = new SqliteHabitDAO(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    void newDatabaseContainsAllDefaultHabits() {
        assertEquals(21, habitDAO.getAllHabits().size());
    }

    @Test
    void defaultHabitsIncludeExpectedHabit() {
        boolean found = habitDAO.getAllHabits().stream()
                .anyMatch(habit -> habit.getName().equals("Use public transport"));

        assertTrue(found);
    }

    @Test
    void creatingDaoAgainDoesNotDuplicateDefaultHabits() {
        new SqliteHabitDAO(connection);

        assertEquals(21, habitDAO.getAllHabits().size());
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