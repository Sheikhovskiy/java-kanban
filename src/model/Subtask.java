package model;

import java.util.Objects;

public class Subtask extends Task {

    private int epicId;

    public Subtask() {

    }

    public Subtask(String taskName, String taskDescription, TaskStatus status) {
        super(taskName, taskDescription, status);

    }

    public Subtask(String taskName, String taskDescription, TaskStatus status, int id) {
        super(taskName, taskDescription, status);
        setEpicId(id);

    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int id) {
        this.epicId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subtask subtask = (Subtask) o;
        return epicId == subtask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(epicId);
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicId=" + epicId +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", status=" + status +
                ", taskId=" + taskId +
                '}';
    }
}
