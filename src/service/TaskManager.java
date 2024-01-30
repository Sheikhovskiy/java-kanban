package service;
import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Objects;

public class TaskManager {

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    int idCount = 0;

    public int generateId(){
        return ++idCount;
    }

    public TaskManager() {
    }

    public HashMap<Integer, Task> printListOfAllTasks() {
        return tasks;
    }
    public HashMap<Integer, Subtask> printListOfAllSubtasks() {
        return subtasks;
    }
    public HashMap<Integer, Epic> printListOfAllEpic() {
        return epics;
    }

    public void deleteAllTasks() {
        tasks.clear();
    }
    public void deleteAllSubtasks() {
        subtasks.clear();
        for (int epicId : epics.keySet()) {
            calculateStatus(getEpicPerId(epicId));
        }
    }
    public void deleteAllEpics() {
        subtasks.clear();
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

        Epic savedEpic = getEpicPerId(subtask.getEpicId());
        if (savedEpic == null) {
            return subtask;
        }
        int epicId = savedEpic.getTaskId();
        savedEpic.setSubtasks(subtask);
        subtasks.put(subtask.getTaskId(), subtask);
        calculateStatus(getEpicPerId(subtask.getEpicId()));
        return subtask;
    }

    public Epic createEpic(Epic epic) {
        epic.setTaskId(generateId());
        epics.put(epic.getTaskId(), epic);
        return epic;
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
        epics.put(saved.taskId, epic);
    }

    public void subtaskUpdate(Subtask subtask) {
        Subtask saved = subtasks.get(subtask.getTaskId());

        if (saved == null) {
            return;
        }

        if (getEpicPerId(subtask.getEpicId()) != null) {
            System.out.println("Такого эпика не существует.");
            return;
        }

        saved.setTaskName(subtask.getTaskName());
        saved.setTaskName(subtask.getTaskDescription());
        saved.setStatus(subtask.getStatus());
        subtasks.put(subtask.getTaskId(), subtask);

        calculateStatus(getEpicPerId(subtask.getEpicId()));
    }

    public void deleteTaskById(int taskId) {
        if (!tasks.containsKey(taskId)){
            System.out.println("Такого id не существует");
            return;
        }
        tasks.remove(taskId);
    }

    public void deleteSubtaskById(int subtaskId) {
        if (!subtasks.containsKey(subtaskId)) {
            System.out.println("Такого id не существует");
            return;
        }
        Subtask subtask = getSubtaskPerId(subtaskId);
        int epicId = getSubtaskPerId(subtaskId).getEpicId();

        Epic epic = epics.get(epicId);
        epic.getSubtasks().remove(subtask);

        subtasks.remove(subtaskId);
        calculateStatus(getEpicPerId(epicId));
    }

    public void deleteEpicById(int epicId) {
        if (!epics.containsKey(epicId)){
            System.out.println("Такого id не существует");
            return;
        }

        for (Subtask sub : epics.get(epicId).getSubtasks()) {
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
