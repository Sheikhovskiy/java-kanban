package model;

public enum TaskStatus {
<<<<<<< HEAD
    NEW,
    IN_PROGRESS,
    DONE
}
=======
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
>>>>>>> 4c2c45b (Небольшие доработки)
