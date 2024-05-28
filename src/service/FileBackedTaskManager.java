package service;

import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import java.util.*;

import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {

    public void save() {





    }

    //new Task("Задача 2", "Описание 2",TaskStatus.IN_PROGRESS);

    String toString(Task task) {

        //if (task.getClass().equals(Task))

        return String.valueOf(task.getTaskId())
                + task.getClass()
                + task.getStatus()
                + task.getTaskDescription();
                // + task.getEpic();

    }


/*    Task fromString(String value) {
        StringBuilder stringBuilder = new StringBuilder(100);


    }*/



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
