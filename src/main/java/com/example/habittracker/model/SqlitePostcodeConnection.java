package com.example.habittracker.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class SqlitePostcodeConnection {
    private static final Connection INSTANCE = openConnection();

    private SqlitePostcodeConnection() {
    }

    public static Connection getInstance() {
        return INSTANCE;
    }

    private static Connection openConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:sqlite::resource:qld_postcodes_4000_4999.sqlite"
            );
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to open the bundled postcode database.", e);
        }
    }
}
