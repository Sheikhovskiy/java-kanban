package service;
import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;

import java.util.HashMap;
import java.util.ArrayList;
public class TaskManager {


    // Хранить все задачи
    HashMap<Integer, Task> tasks;
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();

    int idCount = 0;

    public int generateId(){
        return ++idCount;
    }

    public TaskManager() {
        this.tasks = new HashMap<>();
    }

    // Методы для Задачи/Эпика/Подзадача

    public void printListOfAllTasks() {
        System.out.println(tasks);
    }
    public void printListOfAllSubtasks() {
        System.out.println(subtasks);
    }
    public void printListOfAllEpic() {
        System.out.println(epics);
    }

    public void deleteAllTasks() {
        tasks.clear();
    }
    public void deleteAllSubtasks() {
        subtasks.clear();
    }
    public void deleteAllEpics() {
        epics.clear();
    }

    public Task getTaskPerId(int id) {
        return tasks.get(id);
    }
    public Subtask getSubtaskPerId(int id) {
        return subtasks.get(id);
    }
    public Epic getEpicPerId(int id) {
        return epics.get(id);
    }

    public Task createTask(Task task) {
        task.setTaskId(generateId());
        tasks.put(task.getTaskId(), task);
        return task;
    }

    public Subtask createSubtask(Subtask subtask) {
        subtask.setTaskId(generateId());
        Epic epic = subtask.getEpic();
        epic.setSubtasks(epic.getSubtasks());
        tasks.put(subtask.getTaskId(), subtask );
        calculateStatus(epic);
        return subtask;

    }

    public void taskUpdate(Task task) {
        tasks.put(task.getTaskId(), task);
    }

    public void epicUpdate(Epic epic) {
        Epic saved = epics.get(epic.getTaskId());
        if (saved == null) {
            return;
        }
        saved.setTaskName(epic.getTaskName());
        saved.setTaskDescription(epic.getTaskDescription());
        epics.put(epic.taskId, epic);
        //epics.put(saved.taskId, epic);

    }

    public void subtaskUpdate(Subtask subtask) {
        subtasks.put(subtask.getTaskId(), subtask);


    }

    public void taskDeleteId(int taskId) {
        if (!tasks.containsKey(taskId)){
            System.out.println("Такого id не существует");
            return;
        }
        tasks.remove(taskId);
    }

    public void subtaskDeleteId(int subtaskId) {
        if (!subtasks.containsKey(subtaskId)) {
            System.out.println("Такого id не существует");
            return;
        }
        subtasks.remove(subtaskId);
    }

    public void epicDeleteId(int epicId) {
        if (!epics.containsKey(epicId)){
            System.out.println("Такого id не существует");
            return;
        }
        epics.remove(epicId);

    }

    public ArrayList<Subtask> getSubtaskPerEpic(Epic epic) {
        return epic.getSubtasks();
    }

    private static void calculateStatus(Epic epic) {
        ArrayList<Subtask> subtaskList = epic.getSubtasks();

        for (Subtask substack : subtaskList) {
            if (subtaskList.isEmpty() || substack.status.equals(TaskStatus.NEW)) {
                epic.setStatus("NEW");

            } else if (substack.status.equals(TaskStatus.IN_PROGRESS)) {
                epic.setStatus("IN_PROGRESS");

            } else if (substack.status.equals(TaskStatus.DONE)) {
                epic.setStatus("DONE");
            }
        }



    }


}
