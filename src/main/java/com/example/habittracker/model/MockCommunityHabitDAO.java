package com.example.habittracker.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

public class MockCommunityHabitDAO implements ICommunityHabitDAO {

    public Integer getCommunityHabitID(int postcode) {
        return 17;
    }

    public int getTotalCommunityCompletions(int postcode, LocalDate date) {
        return 1;
    }
}

