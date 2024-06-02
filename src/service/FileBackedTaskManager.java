package service;
import model.*;
import java.io.*;
import java.nio.file.Files;
import java.util.*;

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
                bufferedWriter.newLine();
            }

            for (Subtask subtask : subtasks.values()) {
                bufferedWriter.write(toString(subtask));
                bufferedWriter.newLine();
            }

            for (Epic epic : epics.values()) {
                bufferedWriter.write(toString(epic));
                bufferedWriter.newLine();
            }

            bufferedWriter.newLine();

            for (Task historyTask : getHistory()) {
                bufferedWriter.write(String.format("%s ", Integer.valueOf(historyTask.getTaskId())));

            }

    } catch (IOException exception) {
            throw new ManagerSaveException("Произошла ошибка при сохранение файлов.");
        }

}


    public static FileBackedTaskManager loadFromFile(File inputFile) {

        final FileBackedTaskManager loadedTaskManager = new FileBackedTaskManager(inputFile);
        int maxId = 0;

        try  {

            String content = Files.readString(inputFile.toPath());
            if (Objects.equals(content, "")) return loadedTaskManager;

            String[] lines = content.split(System.lineSeparator());

            List<Integer> historyRecovered = new ArrayList<>();

            for (int i = 0; i < lines.length; i++) {



                String line = lines[i];

                if (line.isEmpty()) {
                    String historyString = lines[i + 1];
                    String[] historyId = historyString.split(" ");

                    for (String id : historyId) {
                        historyRecovered.add(Integer.valueOf(id));
                    }
                    break;
                }

                Task taskRecovered = fromString(lines[i]);


                int idRecovered = taskRecovered.getTaskId();

                if (maxId < idRecovered) {
                    maxId = idRecovered;
                }

                switch (taskRecovered.getType()) {

                    case TASK:
                        loadedTaskManager.tasks.put(idRecovered, taskRecovered);
                        break;
                    case SUBTASK:
                        loadedTaskManager.subtasks.put(idRecovered, (Subtask) taskRecovered);
                        break;
                    case EPIC:
                        loadedTaskManager.epics.put(idRecovered, (Epic) taskRecovered);
                        break;

                }


                for (Map.Entry<Integer, Subtask> entry : loadedTaskManager.subtasks.entrySet()) {
                    Subtask subtask = entry.getValue();
                    Epic epic = loadedTaskManager.epics.get(subtask.getEpicId());
                    epic.setSubtasks(subtask);
                    loadedTaskManager.epicUpdate(loadedTaskManager.epics.get(subtask.getEpicId()));

                }

            }
                for (Integer taskId : historyRecovered) {

                    Task task = loadedTaskManager.tasks.get(taskId);
                    if (task != null) {
                        loadedTaskManager.historyManager.add(task);
                    }
                    Subtask subtask = loadedTaskManager.subtasks.get(taskId);
                    if (subtask != null) {
                        loadedTaskManager.historyManager.add(subtask);
                    }
                    Epic epic = loadedTaskManager.epics.get(taskId);
                    if (epic != null) {
                        loadedTaskManager.historyManager.add(epic);
                    }

                }

            loadedTaskManager.idCount = maxId;


        } catch (IOException exception) {
            throw new ManagerSaveException("Произошла ошибка при чтении файлов.");
        }

        return loadedTaskManager;
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
