package model;

import java.time.Instant;
import java.util.Objects;

public class Subtask extends Task {

    private int epicId;

    public Subtask() {

    }

    public Subtask(String taskName, String taskDescription, TaskStatus status, int id, Instant startTime, int duration) {

        super(taskName, taskDescription, status, id, startTime, duration);
    }

    public Subtask(String taskName, String taskDescription, TaskStatus status, Instant startTime, int duration) {

        super(taskName, taskDescription, status,  startTime, duration);
    }

    public Subtask(int epicId, String taskName, String taskDescription, TaskStatus status) {
        super(taskName, taskDescription, status);
        this.epicId = epicId;
    }

    public Subtask(String taskName, String taskDescription, TaskStatus status) {
        super(taskName, taskDescription, status);

    }


    @Override
    public TaskType getType() {
        return TaskType.SUBTASK;
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
        return getTaskId() == subtask.getTaskId();
//        return epicId == subtask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(epicId);
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicId=" + epicId +
                ", taskName='" + getTaskName() + '\'' +
                ", taskDescription='" + getTaskDescription() + '\'' +
                ", status=" + getStatus() +
                ", taskId=" + getTaskId() +
                '}';
    }
}
