package model;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {

    protected ArrayList<Integer> subtasksId = new ArrayList<>();


    public Epic(String taskName, String taskDescription, TaskStatus status) {

        super(taskName, taskDescription, status);
    }

    public Epic(String taskName, String taskDescription, TaskStatus status, int id) {

        super(taskName, taskDescription, status, id);
    }

    public Epic() {

    }
    public ArrayList<Integer> getSubtasks() {
        return subtasksId;
    }

    public void setSubtasks(Subtask subtask) {

        this.subtasksId.add(subtask.getTaskId());
    }


    @Override
    public String toString() {
        return "Epic{" +
                "subtasksId=" + subtasksId +
                ", taskName='" + getTaskName() + '\'' +
                ", taskDescription='" + getTaskDescription() + '\'' +
                ", status=" + getStatus() +
                ", taskId=" + getTaskId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtasksId, epic.subtasksId);
    }

}