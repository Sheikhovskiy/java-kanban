package service;
import service.InMemoryHistoryManager;

public class Managers {


    public static TaskManager getDefaultTaskManager() {
        return new InMemoryTaskManager(getDefaultHistory());

    }

    public static InMemoryHistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }



}
