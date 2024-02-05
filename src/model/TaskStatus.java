package model;

public enum TaskStatus {

    NEW("Новый"),
    IN_PROGRESS("В процессе"),
    DONE("Завершён");


    String nameStatus;

    TaskStatus(String nameStatus) {

        this.nameStatus = nameStatus;
    }

    String getName() {

        return nameStatus;
    }

}
