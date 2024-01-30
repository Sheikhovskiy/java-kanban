package model;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {

    protected ArrayList<Subtask> subtasks = new ArrayList<>();


    public Epic(String taskName, String taskDescription, TaskStatus status) {

        super(taskName, taskDescription, status);
    }

    public Epic(String taskName, String taskDescription, TaskStatus status, int id) {

        super(taskName, taskDescription, status, id);
    }

    public Epic() {

    }
    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(Subtask subtask) {
        this.subtasks.add(subtask);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtasks=" + subtasks +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", status=" + status +
                ", taskId=" + taskId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtasks, epic.subtasks);
    }

}