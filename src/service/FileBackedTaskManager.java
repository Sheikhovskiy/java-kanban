package service;
import model.*;
import java.io.*;
import OwnExceptions.ManagerSaveException;


public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {

    public FileBackedTaskManager() {

    }
    public FileBackedTaskManager(File csvFile) {
        this.dataFile = csvFile;
    }

    File dataFile;

    public void save() {

        try (FileWriter fileWriter = new FileWriter(dataFile);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            for (Task task : tasks.values()) {
                bufferedWriter.write(toString(task));
//                bufferedWriter.newLine();
            }

            for (Subtask subtask : subtasks.values()) {
                bufferedWriter.write(toString(subtask));
            }

            for (Epic epic : epics.values()) {
                bufferedWriter.write(toString(epic));
            }


    } catch (IOException exception) {
            throw new ManagerSaveException("Произошла ошибка при сохранение файлов.");
        }

}


    public static FileBackedTaskManager loadFromFile(File inputFile) {

        final FileBackedTaskManager fileBackedTaskManagerForLoad = new FileBackedTaskManager(inputFile);

        try (FileReader reader = new FileReader(fileBackedTaskManagerForLoad.dataFile);
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            int maxId = 0;

            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();

                Task undefinedTask = fromString(line);

                if (maxId < undefinedTask.getTaskId()) {
                    maxId = undefinedTask.getTaskId();
                }

                if (undefinedTask.getType() == TaskType.TASK) {
                    fileBackedTaskManagerForLoad.tasks.put(undefinedTask.getTaskId(), undefinedTask);

                } else if (undefinedTask.getType() == TaskType.SUBTASK) {
                    fileBackedTaskManagerForLoad.subtasks.put(undefinedTask.getTaskId(), (Subtask) undefinedTask);

                } else if (undefinedTask.getType() == TaskType.EPIC) {
                    fileBackedTaskManagerForLoad.epics.put(undefinedTask.getTaskId(), (Epic) undefinedTask);
                }

            }
            fileBackedTaskManagerForLoad.idCount = maxId;

        } catch (IOException exception) {
            throw new ManagerSaveException("Произошла ошибка при чтении файлов.");
        }

        return fileBackedTaskManagerForLoad;
    }

    String toString(Task task) {

        String result =
                String.valueOf(task.getTaskId()) + ","
                + task.getType() + ","
                + task.getTaskName() + ","
                + task.getStatus() + ","
                + task.getTaskDescription() + ",";

                if (task.getType() == TaskType.SUBTASK){
                    result += getSubtaskPerId(task.getTaskId()).getEpicId();
                } else {
                    result += "null";
                }
        return result +"\n";
    }

    static Task fromString(String value) {
        String[] receivedStr = value.split(",");

        Task task = null;
        TaskType type = TaskType.valueOf(receivedStr[1]);


        switch (type) {

            case TASK:
                task = new Task(receivedStr[2], receivedStr[4], TaskStatus.valueOf(receivedStr[3]), Integer.valueOf(receivedStr[0]));
                break;

            case SUBTASK:
                task = new Subtask(receivedStr[2], receivedStr[4], TaskStatus.valueOf(receivedStr[3]), Integer.valueOf(receivedStr[0]));
                ((Subtask) task).setEpicId(Integer.valueOf(receivedStr[5]));
                break;

            case EPIC:
                task = new Epic(receivedStr[2], receivedStr[4], TaskStatus.valueOf(receivedStr[3]), Integer.valueOf(receivedStr[0]));
                break;

        }
        return task;
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }
    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }
    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }
    @Override
    public void taskUpdate(Task task) {
        super.taskUpdate(task);
        save();
    }
    @Override
    public void epicUpdate(Epic epic) {
        super.epicUpdate(epic);
        save();
    }
    @Override
    public void subtaskUpdate(Subtask subtask) {
        super.subtaskUpdate(subtask);
        save();
    }
    @Override
    public void deleteTaskById(int taskId) {
        super.deleteTaskById(taskId);
        save();
    }
    @Override
    public void deleteSubtaskById(int subtaskId) {
        super.deleteSubtaskById(subtaskId);
        save();
    }
    @Override
    public void deleteEpicById(int epicId) {
        super.deleteEpicById(epicId);
        save();
    }

}
