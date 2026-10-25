package com.example.habittracker.model;

import java.sql.*;
import java.time.LocalDate;

public class SqliteCommunityHabitDAO implements ICommunityHabitDAO{
    private final Connection connection;

    public SqliteCommunityHabitDAO(){
        connection = SqliteConnection.getInstance();
        createTable();
        addDefaultValues();
    }

    private void createTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS community_habits ("
                    + "postcode	INTEGER NOT NULL, "
                    + "habit_id	INTEGER, "
                    + "PRIMARY KEY(postcode), "
                    + "CONSTRAINT habit_id FOREIGN KEY(habit_id) REFERENCES habits(habit_id))";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * To be removed and replaced with actual "Admin setting" functionality in a formal release.
     * For this prototype, just sets all postcodes with a "Default" habit if none exists.
     */
    private void addDefaultValues() {
        // Create table if not exists
        try {
            for(int i=4000; i<5000; i++){
                String query = "INSERT OR IGNORE INTO community_habits(postcode, habit_id) VALUES (?, 17)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, i);
                statement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Integer getCommunityHabitID(int postcode){
        Integer habitID = null;

        try{
            String query = "SELECT habit_id "
                    + "FROM community_habits "
                    + "WHERE postcode = ? ";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, postcode);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                habitID = resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return habitID;
    }

    public int getTotalCommunityCompletions(int postcode, LocalDate date){
        int totalCompletions = 0;
        Integer habitID = getCommunityHabitID(postcode);

        if (habitID==null){
            return 0;
        }

        try{
            String query = "SELECT COUNT (*) "
                    + "FROM habit_entries LEFT JOIN users ON habit_entries.user_id==users.user_id "
                    + "WHERE postcode = ? "
                    + "AND habit_id = ? "
                    + "AND completion_date = ? ";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, postcode);
            statement.setInt(2, habitID);
            statement.setString(3, date.toString());
            System.out.println(statement);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                totalCompletions = resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalCompletions;
    }

    }

