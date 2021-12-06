package com.example.fit_4_life;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import Users.Instructor;
import Users.Member;


public class GymClass {
    private int id;
    private String name;
    private String description;
    private String date;
    private String starttime;
    private String endtime;
    private int capacity;
    private int instructor;

    public GymClass (int id, String name, String description, String date, String starttime, String endtime, int capacity, int instructor) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.starttime = starttime;
        this.endtime = endtime;
        this.instructor = instructor;
        this.capacity = capacity;
    }

    public GymClass() {

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getStarttime() {
        return starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getInstructor() {
        return instructor;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) { this.name = name; }

    public void setDescription(String description) { this.description = description; }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setInstructor(int instructor) {
        this.instructor = instructor;
    }
}
