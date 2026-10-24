package com.example.habittracker.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqliteHabitEntryDAO implements IHabitEntryDAO {
    private Connection connection;

    public SqliteHabitEntryDAO() {
        connection = SqliteConnection.getInstance();
        createTable();
    }

    private void createTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS habit_entries ("
                    + "user_id INTEGER NOT NULL, "
                    + "habit_id INTEGER NOT NULL, "
                    + "completion_date TEXT NOT NULL, "
                    + "PRIMARY KEY (user_id, habit_id, completion_date), "
                    + "FOREIGN KEY (user_id) REFERENCES users(user_id), "
                    + "FOREIGN KEY (habit_id) REFERENCES habits(habit_id)"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isHabitCompleted(int userId, int habitId, LocalDate date) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT EXISTS (SELECT 1 FROM habit_entries "
                            + "WHERE user_id = ? "
                            + "AND habit_id = ? "
                            + "AND completion_date = ?"
                            + ")"
            );

            statement.setInt(1, userId);
            statement.setInt(2, habitId);
            statement.setString(3, date.toString());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void completeHabit(int userId, int habitId, LocalDate date) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT OR IGNORE INTO habit_entries (user_id, habit_id, completion_date) VALUES (?, ?, ?)"
            );
            statement.setInt(1, userId);
            statement.setInt(2, habitId);
            statement.setString(3, date.toString());
            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void uncompleteHabit(int userId, int habitId, LocalDate date) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM habit_entries WHERE user_id = ? AND habit_id = ? AND completion_date = ?"
            );
            statement.setInt(1, userId);
            statement.setInt(2, habitId);
            statement.setString(3, date.toString());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Integer> getCompletedHabitIds(int userId, LocalDate date) {
        List<Integer> habitIds = new ArrayList<>();

        try {
            String query = "SELECT habit_id "
                    + "FROM habit_entries "
                    + "WHERE user_id = ? "
                    + "AND completion_date = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, userId);
            statement.setString(2, date.toString());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("habit_id");
                habitIds.add(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return habitIds;
    }

    @Override
    public List<LocalDate> getActiveDates(int userId) {
        List<LocalDate> dates = new ArrayList<>();

        String query = "SELECT DISTINCT completion_date "
                + "FROM habit_entries "
                + "WHERE user_id = ? "
                + "ORDER BY completion_date DESC";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    dates.add(LocalDate.parse(resultSet.getString("completion_date")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dates;
    }

    @Override
    public int getUserMonthlyScore(int userId, int month, int year) {
        int score = 0;

        try{
            String query = "SELECT COUNT(*) "
                    + "FROM habit_entries "
                    + "WHERE user_id = ? "
                    + "AND strftime('%m', completion_date) = ? "
                    + "AND strftime('%Y', completion_date) = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, userId);
            statement.setString(2, String.format("%02d", month));
            statement.setString(3, String.valueOf(year));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                score = resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return score;
    }


    @Override
    public Map<Integer, Integer> countCompletionsByHabit(int userId, LocalDate from, LocalDate to) {
        Map<Integer, Integer> counts = new HashMap<>();

        try {
            String query = "SELECT habit_id, COUNT(*) AS completions "
                    + "FROM habit_entries "
                    + "WHERE user_id = ? "
                    + "AND completion_date BETWEEN ? AND ? "
                    + "GROUP BY habit_id";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, userId);
            statement.setString(2, from.toString());
            statement.setString(3, to.toString());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                counts.put(resultSet.getInt("habit_id"), resultSet.getInt("completions"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return counts;
    }

    @Override
    public LocalDate getLastCompletionDate(int userId, int habitId) {
        try {
            String query = "SELECT MAX(completion_date) AS last_date "
                    + "FROM habit_entries "
                    + "WHERE user_id = ? "
                    + "AND habit_id = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, userId);
            statement.setInt(2, habitId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next() && resultSet.getString("last_date") != null) {
                return LocalDate.parse(resultSet.getString("last_date"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<LeaderboardEntry> getMonthlyLeaderboard(int month, int year) {
        List<LeaderboardEntry> leaderboard = new ArrayList<>();

        try{
            String query = "SELECT users.username, COUNT(habit_entries.habit_id) AS score "
                    + "FROM users "
                    + "LEFT JOIN habit_entries "
                    + "ON users.user_id = habit_entries.user_id "
                    + "AND strftime('%m', completion_date) = ? "
                    + "AND strftime('%Y', completion_date) = ? "
                    + "GROUP BY users.user_id, users.username "
                    + "ORDER BY score DESC";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, String.format("%02d", month));
            statement.setString(2, String.valueOf(year));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                int score = resultSet.getInt("score");
                leaderboard.add(new LeaderboardEntry(username, score));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return leaderboard;
    }

}
