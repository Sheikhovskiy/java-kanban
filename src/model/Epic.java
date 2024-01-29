package model;

import java.util.ArrayList;

public class Epic extends Task {

    public ArrayList<Subtask> subtasks = new ArrayList<>();


    public Epic(String taskName, String taskDescription, TaskStatus status,
                ArrayList<Subtask> subtasks) {

        super(taskName, taskDescription, status);
        this.subtasks = subtasks;

    }
    public Epic() {

    }
    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(ArrayList<Subtask> subtasks) {
        this.subtasks = subtasks;
    }



}