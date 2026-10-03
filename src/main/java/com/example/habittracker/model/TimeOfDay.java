package com.example.habittracker.model;

import java.time.LocalTime;

/**
 * Works out which part of the day a time falls in, for greeting the user.
 */
public class TimeOfDay {
    private static final int AFTERNOON_START_HOUR = 12;
    private static final int EVENING_START_HOUR = 18;

    private TimeOfDay() {
    }

    /**
     * @param time The time to get the greeting for.
     */
    public static String greetingFor(LocalTime time) {
        int hour = time.getHour();
        if (hour < AFTERNOON_START_HOUR) {
            return "Morning";
        }
        if (hour < EVENING_START_HOUR) {
            return "Afternoon";
        }
        return "Evening";
    }
}
