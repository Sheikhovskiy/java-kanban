package model;

import service.TaskManager;

public class Task {
    TaskManager taskManager = new TaskManager();
    public String taskName;
    public String taskDescription;
    public String status;
    public int taskId;

    public Task(String taskName, String taskDescription, String status) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.status = status;
    }

    public Task() {
    }

    public String getTaskName() { return taskName; }

    public void setTaskName(String taskName) { this.taskName = taskName; }

    public String getTaskDescription() { return taskDescription; }

    public void setTaskDescription(String taskDescription) { this.taskDescription = taskDescription; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public void setTaskId(int id) {
        this.taskId = id;
    }

    public int getTaskId() {
        return taskId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskManager=" + taskManager +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", status='" + status + '\'' +
                ", taskId=" + taskId +
                '}';
    }

/*    public enum model.TaskStatus {
        NEW,
        IN_PROGRESS,
        DONE
    }*/
}
