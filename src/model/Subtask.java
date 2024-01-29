package model;

public class Subtask extends Task {

    Epic epic;

    public Subtask() {

    }

    public Subtask(String taskName, String taskDescription, TaskStatus status) {
        super(taskName, taskDescription, status);
    }

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }


}
