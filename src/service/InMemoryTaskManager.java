package service;
import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public List<Task> historyList = new ArrayList<>(10);

    InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

    int idCount = 0;

    public int generateId(){
        return ++idCount;
    }

    public InMemoryTaskManager(HistoryManager historyMangager) {
        //this.historyStorage = historyMangager;
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();

    }

    @Override
    public List<Task> getHistory() {
        return historyList;
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
        inMemoryHistoryManager.add(subtasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Subtask getSubtaskPerId(int id) {


        inMemoryHistoryManager.add(subtasks.get(id));
/*        if (historyList.size() < 10) {
            historyList.add(subtasks.get(id));
        } else if (historyList.size() == 10) {

            for (int i = 0; i < historyList.size(); i++) {
                if (i == 8) {
                    historyList.add(historyList.get(i+1));

                } else {
                    historyList.add(historyList.get(i+1));
                }
            }

        }*/

        return subtasks.get(id);
    }

    @Override
    public Epic getEpicPerId(int id) {
        inMemoryHistoryManager.add(subtasks.get(id));
        return epics.get(id);
    }
    @Override
    public Task createTask(Task task) {
        task.setTaskId(generateId());
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
        int epicId = savedEpic.getTaskId();
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
    }
    @Override
    public void deleteSubtaskById(int subtaskId) {
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
        subtasks.remove(subtaskId);

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
                if (countInProgress == listLength){
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
