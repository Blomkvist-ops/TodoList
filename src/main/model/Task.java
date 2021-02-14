package model;

import java.util.Calendar;
import java.util.Date;

public class Task {
    String name;
    //int hours;
    String description;
    int type;
    int status;
    //Calendar deadline = Calendar.getInstance();

    //MODIFIES: this
    //EFFECTS: construct a new task;
    //         type = 0 means this task is important and urgent;
    //         type = 1 means this task is important but not urgent;
    //         type = 2 means this task is not important but urgent;
    //         type = 3 means this task is not important and not urgent.
    public Task(String name, int type) {
        this.name = name;
        this.type = type;
        this.status = 0;

    }


    //MODIFIES: this
    //EFFECTS: add description to this task
    public void setDescription(String description) {
        this.description = description;
    }

    //MODIFIES: this
    //EFFECTS: change type of this task
    public void setType(int type) {
        this.type = type;
    }

    //MODIFIES: this
    //EFFECTS: set status of this task;
    //         status = 0 means this task hasn't started yet;
    //         status = 1 means this task is in progress;
    //         status = 2 means this task has been completed.
    public void setStatus(int status) {
        this.status = status;
    }



    //EFFECTS: get the name of this task
    public String getName() {
        return name;
    }

    //EFFECTS: get the type of this task
    public int getType() {
        return type;
    }


    //EFFECTS: get the description of this task
    public String getDescription() {
        return description;
    }


    //EFFECTS: get the status of this task
    public int getStatus() {
        return status;
    }





}
