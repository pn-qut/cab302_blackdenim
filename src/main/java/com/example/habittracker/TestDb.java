package com.example.habittracker;

import com.example.habittracker.model.UserDAO;
import com.example.habittracker.model.User;
import com.example.habittracker.model.HabitLogDAO;
import com.example.habittracker.model.HabitLog;

public class TestDb {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        userDAO.createTable();
        userDAO.addUser(new User("testuser1", "password123"));
        System.out.println("User found: " + userDAO.findByUsername("testuser1"));

        HabitLogDAO habitLogDAO = new HabitLogDAO();
        habitLogDAO.createTable();
        habitLogDAO.insert(new HabitLog(1, "2026-09-13", true, null));
        System.out.println("Logs: " + habitLogDAO.getAllForUserHabit(1));
    }
}