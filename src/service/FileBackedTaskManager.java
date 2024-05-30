package service;

import model.*;
import org.w3c.dom.Node;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {

    public void save() {
        // Создать файл
        // Проверка на существующий файл

        try (Writer fileWriter = new FileWriter("Saved_Data.csv", true)) {

            List<Task> historyList = getHistory();

            for (Task task : historyList) {
                fileWriter.write(toString(task));
            }

    } catch (IOException exception) {
            exception.getMessage();
        }


}




//    public static FileBackedTaskManager loadFromFile(File file) {
//
//    }



    String toString(Task task) {

        String result =
                String.valueOf(task.getTaskId()) + ","
                + task.getType() + ","
                + task.getTaskName() + ","
                + task.getStatus() + ","
                + task.getTaskDescription() + ",";

                if (task.getType() == TaskType.SUBTASK){
                    result +=  getSubtaskPerId(task.getTaskId()).getEpicId();
                } else {
                    result += "null";
                }
        return result;

    }


    Task fromString(String value) {
        StringBuilder stringBuilder = new StringBuilder(100);

        String[] receivedStr = value.split(",");

        Task task = null;
        TaskType type = TaskType.valueOf(receivedStr[1]);


        switch (type) {

            case TASK:
                task = new Task(receivedStr[2], receivedStr[4], TaskStatus.valueOf(receivedStr[3]));
                task.setTaskId(Integer.valueOf(receivedStr[0]));
                break;

            case SUBTASK:
                task = new Subtask(receivedStr[2], receivedStr[4], TaskStatus.valueOf(receivedStr[3]));
                task.setTaskId(Integer.valueOf(receivedStr[0]));
                ((Subtask) task).setEpicId(Integer.valueOf(receivedStr[5]));
                break;

            case EPIC:
                task = new Epic(receivedStr[2], receivedStr[4], TaskStatus.valueOf(receivedStr[3]));
                task.setTaskId(Integer.valueOf(receivedStr[0]));
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
/*    @Override
    public Task getTaskPerId(int id) {
        super.getTaskPerId(id);
        save();
    }
    @Override
    public Subtask getSubtaskPerId(int id) {
        super.getSubtaskPerId(id);
        save();
    }
    @Override
    public Epic getEpicPerId(int id) {
        super.getEpicPerId(id);
        save();
    }
    @Override
    public Task createTask(Task task) {
        super.createTask(task);
        save();
    }
    @Override
    public Subtask createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
    }
    @Override
    public Epic createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }*/
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
/*    @Override
    public List<Subtask> getSubtaskPerEpic(Epic epic) {
        super.getSubtaskPerEpic(epic);
        save();
    }

    @Override
    public List<Task> getHistory() {
        super.getHistory();
        save();
    }*/

}
