package com.example.habittracker.model;

public class Habit {
    private int id;
    private String name;
    // TODO LATER: in future sprint add unit of measurement functionality
    // private String unit

    public Habit(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the habit id number.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id number of the habit.
     * @param id The id to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the habit.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the habit.
     * @param name The name to set.
     */
    public void setId(String name) {
        this.name = name;
    }
}
