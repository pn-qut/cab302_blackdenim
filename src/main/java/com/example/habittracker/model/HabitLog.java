package com.example.habittracker.model;

public class HabitLog {
    private int logId;
    private int userHabitId;
    private String logDate;
    private boolean completed;
    private Integer value;

    public HabitLog(int logId, int userHabitId, String logDate, boolean completed, Integer value) {
        this.logId = logId;
        this.userHabitId = userHabitId;
        this.logDate = logDate;
        this.completed = completed;
        this.value = value;
    }

    public HabitLog(int userHabitId, String logDate, boolean completed, Integer value) {
        this.userHabitId = userHabitId;
        this.logDate = logDate;
        this.completed = completed;
        this.value = value;
    }

    public int getLogId() {
        return logId;
    }

    public int getUserHabitId() {
        return userHabitId;
    }

    public String getLogDate() {
        return logDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "HabitLog{" +
                "logId=" + logId +
                ", userHabitId=" + userHabitId +
                ", logDate='" + logDate + '\'' +
                ", completed=" + completed +
                ", value=" + value +
                '}';
    }
}