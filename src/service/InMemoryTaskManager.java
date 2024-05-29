package service;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {



    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Subtask> subtasks;
    private final HashMap<Integer, Epic> epics;

    InMemoryTaskManager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
    }

    HistoryManager historyManager = Managers.getDefaultHistory();

    int idCount = 0;

    public int generateId(){
        return ++idCount;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public List<Task> printListOfAllTasks() {
        List<Task> listOfAllTasks = new ArrayList<>();

        if (!tasks.isEmpty()) {
            for (Task task : tasks.values()) {
                listOfAllTasks.add(task);
            }
        }

        return listOfAllTasks;
    }


    @Override
    public List<Task> printListOfAllSubtasks() {
        List<Task> listOfAllSubtasks = new ArrayList<>();

        if (!subtasks.isEmpty()) {
            for (Subtask subtask : subtasks.values()) {
                listOfAllSubtasks.add(subtask);
            }
        }

        return listOfAllSubtasks;
    }
    @Override
    public List<Task> printListOfAllEpics() {
        List<Task> listOfAllEpics = new ArrayList<>();

        if (!epics.isEmpty()) {
            for (Epic epic : epics.values()) {
                listOfAllEpics.add(epic);
            }
        }
        return listOfAllEpics;
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }
    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.getSubtasks().clear();
            calculateStatus(epic);
        }
        subtasks.clear();
    }
    @Override
    public void deleteAllEpics() {
        subtasks.clear();
        epics.clear();
    }
    @Override
    public Task getTaskPerId(int id) {

        final Task askedTask = tasks.get(id);

        if (askedTask != null) {
            historyManager.add(askedTask);
        }
        return askedTask;
    }

    @Override
    public Subtask getSubtaskPerId(int id) {
        final Subtask askedSubtask = subtasks.get(id);

        if (askedSubtask != null) {
            historyManager.add(askedSubtask);
        }
        return askedSubtask;
    }

    @Override
    public Epic getEpicPerId(int id) {
        final Epic askedEpic = epics.get(id);


        if (askedEpic != null) {
            historyManager.add(askedEpic);
        }
        return askedEpic;
    }
    @Override
    public Task createTask(Task task) {
        if (task.getTaskId() == null) {
            task.setTaskId(generateId());
        }

        tasks.put(task.getTaskId(), task);

        return task;
    }
    @Override
    public Subtask createSubtask(Subtask subtask) {

        subtask.setTaskId(generateId());

        Epic savedEpic = epics.get(subtask.getEpicId());
        if (savedEpic == null) {
            return subtask;
        }
        savedEpic.setSubtasks(subtask);
        subtasks.put(subtask.getTaskId(), subtask);
        calculateStatus(savedEpic);
        return subtask;
    }
    @Override
    public Epic createEpic(Epic epic) {

        epic.setTaskId(generateId());
        epics.put(epic.getTaskId(), epic);
        return epic;
    }
    @Override
    public void taskUpdate(Task task) {
        tasks.put(task.getTaskId(), task);
    }
    @Override
    public void epicUpdate(Epic epic) {
        Epic saved = epics.get(epic.getTaskId());

        if (saved == null) {
            return;
        }

        saved.setTaskName(epic.getTaskName());
        saved.setTaskDescription(epic.getTaskDescription());

        epics.put(saved.getTaskId(), epic);
    }
    @Override
    public void subtaskUpdate(Subtask subtask) {
        Subtask saved = subtasks.get(subtask.getTaskId());

        if (!subtasks.containsKey(subtask.getTaskId())) {
            return;
        }

        if (saved == null) {
            return;
        }

        if (getEpicPerId(subtask.getEpicId()) == null) {
            System.out.println("Такого эпика не существует.");
            return;
        }

        saved.setTaskName(subtask.getTaskName());
        saved.setTaskName(subtask.getTaskDescription());
        saved.setStatus(subtask.getStatus());
        subtasks.put(subtask.getTaskId(), subtask);

        calculateStatus(epics.get(subtask.getEpicId()));
    }
    @Override
    public void deleteTaskById(int taskId) {
        if (!tasks.containsKey(taskId)){
            System.out.println("Такого id не существует");
            return;
        }

        tasks.remove(taskId);
        historyManager.remove(taskId);
    }
    @Override
    public void deleteSubtaskById(int subtaskId) {

        System.out.println(subtasks);
        if (!subtasks.containsKey(subtaskId)) {
            System.out.println("Такого id не существует");
            return;
        }
        Subtask subtask = subtasks.remove(subtaskId);
        if (subtask == null) {
            return;
        }
        Epic epic = epics.get(subtask.getEpicId());

        epic.getSubtasks().remove(Integer.valueOf(subtaskId));

        //subtasks.remove(subtaskId);
        historyManager.remove(subtaskId);

        calculateStatus(epic);

    }
    @Override
    public void deleteEpicById(int epicId) {
        if (!epics.containsKey(epicId)){
            System.out.println("Такого id не существует");
            return;
        }
        final Epic epic = epics.remove(epicId);
        for (Integer subId : epic.getSubtasks()) {
            subtasks.remove(subId);
        }

        epics.remove(epicId);
        historyManager.remove(epicId);

    }
    @Override
    public ArrayList<Subtask> getSubtaskPerEpic(Epic epic) {

        ArrayList<Subtask> subtasksList = new ArrayList<>();

        for (Integer subId : epic.getSubtasks()) {
            subtasksList.add(subtasks.get(subId));
        }
        return subtasksList;
    }


    public void calculateStatus(Epic epic) {
        ArrayList<Integer> subtasksIdList = epic.getSubtasks();
        int countNew = 0;
        int countInProgress = 0;
        int countDone = 0;

        int listLength = subtasksIdList.size();

        for (Integer subId : subtasksIdList) {

            if (subtasksIdList.isEmpty() || subtasks.get(subId).getStatus().equals(TaskStatus.NEW)) {
                countNew++;
                if (countNew == listLength) {
                    epic.setStatus(TaskStatus.NEW);
                }

            } else if (subtasks.get(subId).getStatus().equals(TaskStatus.IN_PROGRESS)) {
                countInProgress++;
                if (countInProgress == listLength || countInProgress >= 1) {
                    epic.setStatus(TaskStatus.IN_PROGRESS);
                }

            } else if (subtasks.get(subId).getStatus().equals(TaskStatus.DONE)) {
                countDone++;
                if (countDone == listLength) {
                    epic.setStatus(TaskStatus.DONE);
                }
            }
        }
    }
}
