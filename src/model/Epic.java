package model;

import service.InMemoryTaskManager;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {

    protected ArrayList<Integer> subtasksId = new ArrayList<>();



    public Epic(String taskName, String taskDescription, TaskStatus status, int id, Instant startTime, int duration) {

        super(taskName, taskDescription, status, id, startTime, duration);
    }

    public Epic(String taskName, String taskDescription, TaskStatus status, int id) {

        super(taskName, taskDescription, status, id);
    }

    public Epic(String taskName, String taskDescription, TaskStatus status) {

        super(taskName, taskDescription, status);
    }

    public Epic() {

    }
    public ArrayList<Integer> getSubtasks() {
        return subtasksId;
    }

    public void setSubtasks(Subtask subtask) {

        this.subtasksId.add(subtask.getTaskId());

    }

    public void addTask(Subtask subtask) {

        Duration durationTemp;

        for (Integer subId : subtasksId) {

            if (getStartTime().isBefore(startTime)) {
                startTime = subtask.getStartTime();
            }

            if (subtask.getEndTime().isAfter(endTime)) {
                endTime = subtask.getEndTime();
            }


            durationTemp = Duration.between(startTime, endTime);
            this.duration += (int) durationTemp.toMinutes();
        }

    }



//    public void removeTask(Subtask subtask) {
//
//        if (!subtasksId.contains(subtask.getTaskId())) {
//
//            return;
//        }
//
//        subtasksId.remove(subtask);
//
//    }


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

//    public void setStartTime(int startTime) {
//        this.startTime = startTime;
//    }
//
//
//    public void setEndTime(int endTime) {
//        this.endTime = endTime;
//    }


    public Instant getEndTime() {
        return this.endTime;
    }


}