package com.example.anee.mytask.models;

import java.io.Serializable;

public class TodolistPojo implements Serializable {

    private String Task_Id, Name, Location, Time, Date;

    public TodolistPojo(String task_id, String task_name, String task_loc, String task_time, String task_date) {

        this.Task_Id = task_id;
        this.Name = task_name;
        this.Location = task_loc;
        this.Time = task_time;
        this.Date = task_date;
    }

    public String getTask_Id() {
        return Task_Id;
    }

    public void setTask_Id(String task_Id) {
        Task_Id = task_Id;
    }

    public void setName(String name) {
        Name = name;
    }


    public String getName() {
        return Name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
