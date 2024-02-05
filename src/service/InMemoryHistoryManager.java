package service;
<<<<<<< HEAD
import model.Task;
import service.HistoryManager;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final LinkedList<Task> history = new LinkedList<>();

    @Override
    public void add(Task task) {
        if (history.contains(task)) {
            history.remove(task);
        }
        history.add(task);
        if (history.size() > 10) {
            history.removeFirst();
        }
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }
=======

import model.Task;

import java.util.LinkedList;

public class InMemoryHistoryManager {

    LinkedList<Task> history = new LinkedList<>();

    public <T extends Task> void add(T task) {

        if (history.size() > 9) {
            history.removeFirst();
        }
        history.add(task);


    }

    public LinkedList<Task> getAll() {
        return history;
    }


>>>>>>> 4c2c45b (Небольшие доработки)
}
