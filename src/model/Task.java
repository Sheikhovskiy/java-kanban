package model;

import java.util.Objects;

public class Task {
    private String taskName;
    private String taskDescription;
    private TaskStatus status;
    private int taskId;

    public Task(String taskName, String taskDescription, TaskStatus status) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.status = status;
    }

    public Task(String taskName, String taskDescription, TaskStatus status, int id) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.status = status;
        this.taskId = id;
    }

    public Task() {

    }


    public TaskType getType() {
        return TaskType.TASK;
    }


    public String getTaskName() { return taskName; }

    public void setTaskName(String taskName) { this.taskName = taskName; }

    public String getTaskDescription() { return taskDescription; }

    public void setTaskDescription(String taskDescription) { this.taskDescription = taskDescription; }

    public TaskStatus getStatus() { return status; }

    public void setStatus(TaskStatus status) { this.status = status; }

    public void setTaskId(int id) {
        this.taskId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskId == task.taskId ;
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
                '}';
    }
}
