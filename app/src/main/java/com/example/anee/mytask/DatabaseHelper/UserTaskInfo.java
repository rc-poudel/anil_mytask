package com.example.anee.mytask.DatabaseHelper;

public class UserTaskInfo {

    private String taskid;

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getTask_title() {
        return task_title;
    }

    public void setTask_title(String task_title) {
        this.task_title = task_title;
    }

    public String getTask_location() {
        return task_location;
    }

    public void setTask_location(String task_location) {
        this.task_location = task_location;
    }

    public String getTask_latitude() {
        return task_latitude;
    }

    public void setTask_latitude(String task_latitude) {
        this.task_latitude = task_latitude;
    }

    public String getTask_longitude() {
        return task_longitude;
    }

    public void setTask_longitude(String task_longitude) {
        this.task_longitude = task_longitude;
    }

    public String getTask_time() {
        return task_time;
    }

    public void setTask_time(String task_time) {
        this.task_time = task_time;
    }

    public String getTask_date() {
        return task_date;
    }

    public void setTask_date(String task_date) {
        this.task_date = task_date;
    }

    public String getTaskuid() {
        return taskuid;
    }

    public void setTaskuid(String taskuid) {
        this.taskuid = taskuid;
    }

    public String getTask_created_on() {
        return task_created_on;
    }

    public void setTask_created_on(String task_created_on) {
        this.task_created_on = task_created_on;
    }

    private String task_title;
    private String task_location;
    private String task_latitude;
    private String task_longitude;
    private String task_time;
    private String task_date;
    private String taskuid;
    private String task_created_on;
}
