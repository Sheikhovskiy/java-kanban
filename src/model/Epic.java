package model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {

    protected ArrayList<Integer> subtasksId = new ArrayList<>();



    public Epic(String taskName, String taskDescription, int id, Instant startTime, int duration) {

        super(taskName, taskDescription, id, startTime, duration);
    }

    public Epic(String taskName, String taskDescription, int id) {

        super(taskName, taskDescription, id);
    }

    public Epic(String taskName, String taskDescription) {

        super(taskName, taskDescription);
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
    public TaskType getType() {
        return TaskType.EPIC;
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



    public Instant getEndTime() {
        return this.endTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


}