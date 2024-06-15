package service;

import OwnExceptions.NotFoundException;
import model.*;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager {

    protected final HashMap<Integer, Task> tasks;
    protected final HashMap<Integer, Subtask> subtasks;
    protected final HashMap<Integer, Epic> epics;

    InMemoryTaskManager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
    }

    TreeSet<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));


    HistoryManager historyManager = Managers.getDefaultHistory();

    int idCount = 0;

    public int generateId() {
        return ++idCount;
    }

    @Override
    public Task createTask(Task task) {

        Task initial = tasks.get(task.getTaskId());

        if (initial != null) {
            System.out.println("Задача Task id=" + task.getTaskId() + "Уже существует");
            return task;
        }

        if (checkTaskTime(task) ) {
            throw new IllegalStateException("Задача пересекается с уже существующей");
        }

        task.setTaskId(generateId());
        tasks.put(task.getTaskId(), task);

        if (!(task.getStartTime().equals(task.getEndTime()))) { // Проверка что duration != 0, т.к Instant мы по дефолту обозначаем Instant.now();
            prioritizedTasks.add(task);
        }


        return task;
    }

    @Override
    public Subtask createSubtask(Subtask subtask) {

        subtask.setTaskId(generateId());

        Epic savedEpic = epics.get(subtask.getEpicId());
        if (savedEpic == null) {
            return subtask;
        }

        Subtask initial = subtasks.get(subtask.getTaskId());

        if (initial != null) {
            System.out.println("Задача Subtask id=" + subtask.getTaskId() + "Уже существует");
            return subtask;
        }

        if (checkTaskTime(subtask)) {
            throw new IllegalStateException("Задача пересекается с уже существующей");
        }

        savedEpic.setSubtasks(subtask);
        subtasks.put(subtask.getTaskId(), subtask);
        calculateStatus(savedEpic);

        if (!(subtask.getStartTime().equals(subtask.getEndTime()))) {
            prioritizedTasks.add(subtask);
        }


        return subtask;
    }

    @Override
    public void taskUpdate(Task task) {

        Task initial = tasks.get(task.getTaskId());

        if (initial == null) {
            throw new NotFoundException("Task id=" + task.getTaskId());
        }

        if (!checkTaskTime(task) ) {
            prioritizedTasks.remove(initial);
            add(task);
        }
//        prioritizedTasks.remove(initial);
//        prioritizedTasks.add(task);


        tasks.put(task.getTaskId(), task);
    }


    public void add(Task task) {

        if (prioritizedTasks.stream().anyMatch( existingTask -> checkTaskTime(task))) {
            throw new IllegalStateException("Задачи пересекаются");
        } else {
            prioritizedTasks.add(task);
        }

    }

    @Override
    public void subtaskUpdate(Subtask subtask) {
        Subtask initial = subtasks.get(subtask.getTaskId());



        if (!subtasks.containsKey(subtask.getTaskId())) {
            return;
        }

        if (initial == null) {
            throw new NotFoundException("Subtask id=" + subtask.getTaskId());
        }

        if (getEpicPerId(subtask.getEpicId()) == null) {
            System.out.println("Такого эпика не существует.");
            return;
        }

        initial.setTaskName(subtask.getTaskName());
        initial.setTaskName(subtask.getTaskDescription());
        initial.setStatus(subtask.getStatus());
        subtasks.put(subtask.getTaskId(), subtask);


        if (!checkTaskTime(subtask) ) {
            prioritizedTasks.remove(initial);
            add(subtask);
        }


        calculateStatus(epics.get(subtask.getEpicId()));
    }

        @Override
        public boolean checkTaskTime(Task task) { // Проверка на отдельные отрезки времени

        for (Task existing : prioritizedTasks) {
            if (existing.getTaskId() == task.getTaskId()) {
                continue;
            }

            if (!( task.getEndTime().isBefore(existing.getStartTime()) || task.getStartTime().isAfter(existing.getEndTime())) ) {
                return true; // пересекаются
            }
        }
        return false;
    }

/*    public boolean checkTaskTime(Subtask subtask) { // Проверка на отдельные отрезки времени

        for (Task s : prioritizedTasks) {
            if (s.getTaskId() == subtask.getTaskId()) {
                continue;
            }

            if (
                    ( (s.getStartTime().isBefore(subtask.getStartTime()) && s.getStartTime().isBefore(subtask.getEndTime()) )
                            &&  s.getEndTime().isBefore(subtask.getStartTime()) && s.getEndTime().isBefore(subtask.getEndTime()) )

                            || ( (s.getStartTime().isAfter(subtask.getStartTime()) && s.getStartTime().isAfter(subtask.getEndTime()) )
                            && s.getEndTime().isAfter(subtask.getStartTime()) && s.getEndTime().isAfter(subtask.getEndTime()) )

            ) return true;
        }
        return false;
    }*/

    @Override
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
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
    public Epic createEpic(Epic epic) {

        if (epic == null) {
            return epic;

        }

        epic.setTaskId(generateId());
        epics.put(epic.getTaskId(), epic);
        return epic;
    }

/*    @Override
    public void taskUpdate(Task task) {

        tasks.put(task.getTaskId(), task);
    }*/

    @Override
    public void epicUpdate(Epic epic) {
        Epic saved = epics.get(epic.getTaskId());

        if (saved == null) {
            throw new NotFoundException("Epic id=" + epic.getTaskId());
        }

        saved.setTaskName(epic.getTaskName());
        saved.setTaskDescription(epic.getTaskDescription());

        epics.put(saved.getTaskId(), epic);

    }

    @Override
    public void deleteTaskById(int taskId) {
        if (!tasks.containsKey(taskId)) {
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
        if (!epics.containsKey(epicId)) {
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


        return epic.getSubtasks().stream()
                .map(subId -> subtasks.get(subId))
                .collect(Collectors.toCollection(ArrayList::new));

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
