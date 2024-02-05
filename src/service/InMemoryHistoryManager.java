package service;

import model.Task;

import java.util.LinkedList;

public class InMemoryHistoryManager implements HistoryManager {

    LinkedList<Task> history = new LinkedList<>();
    @Override
    public void add(Task task) {

        if (history.size() > 9) {
            history.removeFirst();
        }
        history.add(task);

    }
    @Override
    public LinkedList<Task> getHistory() {
        return history;
    }


}
