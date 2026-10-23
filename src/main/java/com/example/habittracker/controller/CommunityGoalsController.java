package com.example.habittracker.controller;

import com.example.habittracker.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public class CommunityGoalsController {

    @FXML
    private Label communityGoalsTitle;

    @FXML
    private Label communityGoalsHabitLabel;

    @FXML
    private LineChart<String, Integer> communityGoalsChart;

    private final ObservableList<LineChart.Series<String,Integer>> communityGoalsSeries = FXCollections.observableArrayList();
    private final IHabitDAO habitDAO = DAOFactory.getHabitDAO();
    private final ICommunityHabitDAO communityHabitDAO = DAOFactory.getCommunityHabitEntryDAO();

    private Integer postcode;
    private Habit communityHabit;

    public void setUser(User user) {
        postcode = user.getPostcode();
        int communityHabitID = communityHabitDAO.getCommunityHabitID(postcode);
        communityHabit = habitDAO.findHabitById(communityHabitID);
        updatePageHeader();
        getDataForGraph();
        System.out.println(communityGoalsSeries);
    }

    @FXML
    public void initialize() {
        communityGoalsChart.setData(communityGoalsSeries);
    }

    private void updatePageHeader() {
        String userPostcode = postcode.toString();
        communityGoalsTitle.setText("Community Goal Summary for Post Code "+userPostcode);
        communityGoalsHabitLabel.setText("The Community Goal for this month is: " + communityHabit.getName() + "!");
    }

    public void getDataForGraph(){
        YearMonth currentMonth=YearMonth.now();
        LocalDate start = LocalDate.of(currentMonth.getYear(), currentMonth.getMonthValue(),1);
        LocalDate end = LocalDate.of(currentMonth.getYear(), currentMonth.getMonthValue(), currentMonth.atEndOfMonth().getDayOfMonth());
        ArrayList<LocalDate> totalDates = new ArrayList<>();
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