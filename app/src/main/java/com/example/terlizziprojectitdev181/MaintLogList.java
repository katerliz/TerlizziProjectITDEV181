package com.example.terlizziprojectitdev181;


//Data Class for Adapter
public class MaintLogList {

    private String description;

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private boolean done;

}
