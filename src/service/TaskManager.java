package service;
import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;

import java.util.HashMap;
import java.util.ArrayList;
public class TaskManager {


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
        subtasks.put(subtask.getTaskId(), subtask);
        calculateStatus(epic);
        return subtask;

    }

    public Epic createEpic(Epic epic) {
        epic.setTaskId(generateId());
        epics.put(epic.getTaskId(), epic);
        return epic;
    }

    public void taskUpdate(Task task) {
        Task saved = tasks.get(task.getTaskId());

        if (saved == null) {
            return;
        }

        saved.setTaskName(task.getTaskName());
        saved.setTaskDescription(task.getTaskDescription());
        saved.setStatus(task.getStatus());
        tasks.put(task.getTaskId(), task);
    }

    public void epicUpdate(Epic epic) {
        Epic saved = epics.get(epic.getTaskId());

        if (saved == null) {
            return;
        }

        saved.setTaskName(epic.getTaskName());
        saved.setTaskDescription(epic.getTaskDescription());
        saved.setStatus(epic.getStatus());
        epics.put(saved.taskId, epic);
        calculateStatus(epic);

    }

    public void subtaskUpdate(Subtask subtask) {
        Subtask saved = subtasks.get(subtask.getTaskId());

        if (saved == null) {
            return;
        }

        saved.setTaskName(subtask.getTaskName());
        saved.setTaskName(subtask.getTaskDescription());
        saved.setStatus(subtask.getStatus());
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
        ArrayList<Subtask> subList = getEpicPerId(epicId).getSubtasks();

        for (Subtask sub : subList) {
            subtasks.remove(sub.getTaskId());
        }
        epics.remove(epicId);


    }

    public ArrayList<Subtask> getSubtaskPerEpic(Epic epic) {

        return epic.getSubtasks();
    }

    public static void calculateStatus(Epic epic) {
        ArrayList<Subtask> subtaskList = epic.getSubtasks();
        int countNew = 0;
        int countInProgress = 0;
        int countDone = 0;

        int listLength = subtaskList.size();

        for (Subtask substack : subtaskList) {


            if (subtaskList.isEmpty() || substack.status.equals(TaskStatus.NEW)) {
                countNew++;
                if (countNew == listLength) {
                    epic.setStatus(TaskStatus.NEW);
                }

            } else if (substack.status.equals(TaskStatus.IN_PROGRESS)) {
                countInProgress++;
                if (countInProgress == listLength){
                    epic.setStatus(TaskStatus.IN_PROGRESS);
                }

            } else if (substack.status.equals(TaskStatus.DONE)) {
                countDone++;
                if (countDone == listLength) {
                    epic.setStatus(TaskStatus.DONE);
                }
            }
        }

    }

}
