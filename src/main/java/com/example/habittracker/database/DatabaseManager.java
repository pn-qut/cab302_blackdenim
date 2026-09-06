package com.example.habittracker.database;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Handles connecting to the SQLite database and creating the tables
 * defined in db/schema.sql.
 */
public class DatabaseManager {

    private final String jdbcUrl;

    /**
     * Creates a manager for the SQLite database at the given file path.
     * @param dbFilePath Path to the database file. SQLite creates the file if it does not exist.
     */
    public DatabaseManager(String dbFilePath) {
        this.jdbcUrl = "jdbc:sqlite:" + dbFilePath;
    }

    /**
     * Opens a new connection to the database.
     * SQLite turns foreign keys off by default, so they have to be switched on per connection.
     * @return An open connection. The caller is responsible for closing it.
     */
    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(jdbcUrl);
        try (Statement statement = connection.createStatement()) {
            statement.execute("PRAGMA foreign_keys = ON;");
        }
        return connection;
    }

    /**
     * Creates any tables from db/schema.sql that do not already exist.
     * Safe to call more than once.
     */
    public void initializeSchema() {
        try (Connection connection = getConnection()) {
            // Comments are stripped first so a semicolon inside one cannot split a statement in half.
            String schema = readSchema().replaceAll("(?m)--.*$", "");
            for (String sql : schema.split(";")) {
                String trimmed = sql.trim();
                if (!trimmed.isEmpty()) {
                    try (Statement statement = connection.createStatement()) {
                        statement.execute(trimmed);
                    }
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Failed to initialize database schema", e);
        }
    }

    /**
     * Reads the schema.sql file bundled in the application's resources.
     */
    private String readSchema() throws IOException {
        try (InputStream in = DatabaseManager.class.getResourceAsStream("/db/schema.sql")) {
            if (in == null) {
                throw new IOException("Could not find /db/schema.sql on the classpath");
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
