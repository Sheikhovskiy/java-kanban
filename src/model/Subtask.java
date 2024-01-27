package model;

import model.Task;

public class Subtask extends Task {

    Epic epic;

    public Subtask(String taskName, String taskDescription, String status) {
        super(taskName, taskDescription, status);
    }

    public Epic getEpic() {
        return epic; }

    public void setEpic(Epic epic) {
        this.epic = epic; }





}
