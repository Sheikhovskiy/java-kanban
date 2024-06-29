package model;
import java.time.*;

import java.time.temporal.ChronoUnit;

public class Task {
    private String taskName;
    private String taskDescription;
    private TaskStatus status;
    private int taskId;


    protected Instant startTime;
    protected int duration;
    protected Instant endTime;

    public Task(String taskName, String taskDescription, TaskStatus status, int id, Instant startTime, int duration) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.status = status;
        this.taskId = id;

        this.startTime = startTime;
        this.duration = duration;
        this.endTime = startTime.plus(duration, ChronoUnit.MINUTES);

    }

    // SUBTASK & EPIC
    public Task(String taskName, String taskDescription, TaskStatus status, Instant startTime, int duration) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.status = status;

        this.startTime = startTime;
        this.duration = duration;
        this.endTime = startTime.plus(duration, ChronoUnit.MINUTES);
    }

    public Task(String taskName, String taskDescription, TaskStatus status, int id) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.status = status;
        this.taskId = id;

        this.startTime = Instant.now();
        this.duration = 0;
        this.endTime = startTime.plus(duration, ChronoUnit.MINUTES);
    }

    public Task(String taskName, String taskDescription, TaskStatus status) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.status = status;


        this.startTime = Instant.now();
        this.duration = 0;
        this.endTime = startTime.plus(duration, ChronoUnit.MINUTES);
    }

    public Task() {

    }

    public Task(String taskName, String taskDescription, int id, Instant startTime, int duration) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskId = id;

        this.startTime = startTime;
        this.duration = duration;

    }

    public Task(String taskName, String taskDescription, int id) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskId = id;
    }

    public Task(String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
    }



    public TaskType getType() {
        return TaskType.TASK;
    }


    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setTaskId(int id) {
        this.taskId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskId == task.taskId;
    }


    public int getTaskId() {
        return taskId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + taskName + '\'' +
                " ,taskDescription='" + taskDescription + '\'' +
                " ,status='" + status + '\'' +
                " ,taskId=" + taskId +
                " ,taskDuration=" + duration +
                " ,taskStartTime=" + startTime +
                '}';
    }



    public Instant getStartTime() {
        return this.startTime;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    
    public int getDuration() {
        return this.duration;
    }



}