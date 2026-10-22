package com.example.habittracker.controller;

import com.example.habittracker.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Optional;

public class CommunityGoalsController {

    @FXML
    private LineChart<String, Integer> communityGoalsChart;

    private final ObservableList<LineChart.Series<String,Integer>> communityGoalsSeries = FXCollections.observableArrayList();

    private final ICommunityHabitDAO communityHabitDAO=DAOFactory.getCommunityHabitEntryDAO();
    private Integer postcode;

    public void setUser(User user) {
        postcode = user.getPostcode();
        getDataForGraph();
        System.out.println(communityGoalsSeries);
    }

    @FXML
    public void initialize() {
        communityGoalsChart.setData(communityGoalsSeries);
    }

    public void getDataForGraph(){
        YearMonth currentMonth=YearMonth.now();
        LocalDate start = LocalDate.of(currentMonth.getYear(), currentMonth.getMonthValue(),1);
        LocalDate end = LocalDate.of(currentMonth.getYear(), currentMonth.getMonthValue(), currentMonth.atEndOfMonth().getDayOfMonth());
        ArrayList<LocalDate> totalDates = new ArrayList<LocalDate>();
        while (!start.isAfter(end)) {
            totalDates.add(start);
            start = start.plusDays(1);
        }

        ObservableList<LineChart.Data<String,Integer>> communityGoalsData = FXCollections.observableArrayList();
        for(LocalDate date : totalDates) {
            int completions = communityHabitDAO.getTotalCommunityCompletions(postcode, date);
            communityGoalsData.add(new XYChart.Data<>(date.toString(), completions));
        }
        communityGoalsSeries.add(new XYChart.Series<>(communityGoalsData));
    }
}