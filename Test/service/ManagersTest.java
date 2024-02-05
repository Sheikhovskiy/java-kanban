package service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class ManagersTest {

    @Test
    void shouldReturnInitializedManagers() {
        TaskManager taskManager = Managers.getDefaultTaskManager();
        assertNotNull(taskManager, "TaskManager должен быть проинициализирован");

        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager, "HistoryManager должен быть проинициализирован");

    }
}
