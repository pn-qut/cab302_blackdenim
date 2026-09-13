package com.example.habittracker.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HabitLogDAO {
    private Connection connection;

    public HabitLogDAO() {
        connection = DatabaseConnection.getInstance();
    }

    public void createTable() {
        try {
            Statement createTable = connection.createStatement();
            createTable.execute(
                "CREATE TABLE IF NOT EXISTS habit_logs ("
                + "log_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "user_habit_id INTEGER NOT NULL, "
                + "log_date DATE NOT NULL, "
                + "completed BOOLEAN NOT NULL, "
                + "value INTEGER, "
                + "UNIQUE (user_habit_id, log_date), "
                + "FOREIGN KEY (user_habit_id) REFERENCES user_habits(user_habit_id)"
                + ")"
            );
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void insert(HabitLog log) {
        try {
            PreparedStatement insertLog = connection.prepareStatement(
                "INSERT INTO habit_logs (user_habit_id, log_date, completed, value) VALUES (?, ?, ?, ?)"
            );
            insertLog.setInt(1, log.getUserHabitId());
            insertLog.setString(2, log.getLogDate());
            insertLog.setBoolean(3, log.isCompleted());
            if (log.getValue() != null) {
                insertLog.setInt(4, log.getValue());
            } else {
                insertLog.setNull(4, Types.INTEGER);
            }
            insertLog.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void update(HabitLog log) {
        try {
            PreparedStatement updateLog = connection.prepareStatement(
                "UPDATE habit_logs SET completed = ?, value = ? WHERE log_id = ?"
            );
            updateLog.setBoolean(1, log.isCompleted());
            if (log.getValue() != null) {
                updateLog.setInt(2, log.getValue());
            } else {
                updateLog.setNull(2, Types.INTEGER);
            }
            updateLog.setInt(3, log.getLogId());
            updateLog.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void delete(int logId) {
        try {
            PreparedStatement deleteLog = connection.prepareStatement("DELETE FROM habit_logs WHERE log_id = ?");
            deleteLog.setInt(1, logId);
            deleteLog.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public List<HabitLog> getAllForUserHabit(int userHabitId) {
        List<HabitLog> logs = new ArrayList<>();
        try {
            PreparedStatement getLogs = connection.prepareStatement(
                "SELECT * FROM habit_logs WHERE user_habit_id = ? ORDER BY log_date"
            );
            getLogs.setInt(1, userHabitId);
            ResultSet rs = getLogs.executeQuery();
            while (rs.next()) {
                Integer value = rs.getObject("value") != null ? rs.getInt("value") : null;
                logs.add(new HabitLog(
                    rs.getInt("log_id"),
                    rs.getInt("user_habit_id"),
                    rs.getString("log_date"),
                    rs.getBoolean("completed"),
                    value
                ));
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return logs;
    }

    public HabitLog getByUserHabitAndDate(int userHabitId, String logDate) {
        try {
            PreparedStatement getLog = connection.prepareStatement(
                "SELECT * FROM habit_logs WHERE user_habit_id = ? AND log_date = ?"
            );
            getLog.setInt(1, userHabitId);
            getLog.setString(2, logDate);
            ResultSet rs = getLog.executeQuery();
            if (rs.next()) {
                Integer value = rs.getObject("value") != null ? rs.getInt("value") : null;
                return new HabitLog(
                    rs.getInt("log_id"),
                    rs.getInt("user_habit_id"),
                    rs.getString("log_date"),
                    rs.getBoolean("completed"),
                    value
                );
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return null;
    }
}