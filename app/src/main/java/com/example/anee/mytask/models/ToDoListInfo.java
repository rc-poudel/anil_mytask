package com.example.anee.mytask.models;

public class ToDoListInfo {
    private String Id,TaskName, TaskLocation,Tasktime,Taskdate;

    public ToDoListInfo( String taskName, String taskLocation , String tasktime, String taskdate) {

        TaskName = taskName;
        TaskLocation = taskLocation;
        Tasktime = tasktime;
        Taskdate = taskdate;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String taskName) {
        TaskName = taskName;
    }

    public String getTaskLocation() {
        return TaskLocation;
    }

    public void setTaskLocation(String taskLocation) {
        TaskLocation = taskLocation;
    }

    public String getTasktime() {
        return Tasktime;
    }

    public void setTasktime(String tasktime) {
        Tasktime = tasktime;
    }

    public String getTaskdate() {
        return Taskdate;
    }

    public void setTaskdate(String taskdate) {
        Taskdate = taskdate;
    }
}
