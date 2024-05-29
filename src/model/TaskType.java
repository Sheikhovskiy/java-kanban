package model;

public enum TaskType {

    TASK("TASK"),
    SUBTASK("SUBTASK"),
    EPIC("EPIC");

    String typeName;

    TaskType(String typeName) {
        this.typeName = typeName;
    }


    public String getTypeName() {
        return typeName;
    }


}
