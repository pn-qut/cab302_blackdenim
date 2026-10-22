package com.example.habittracker.model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SqliteCommunityHabitDAO implements ICommunityHabitDAO{
    private Connection connection;

    public SqliteCommunityHabitDAO(){
        connection = SqliteConnection.getInstance();
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
        int habitID = getCommunityHabitID(postcode);

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

