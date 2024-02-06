package service;

import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    TaskManager taskManager = Managers.getDefaultTaskManager();

    private Epic epic1;
    private Subtask subtask1;
    private Task task1;


    @BeforeEach
    void setUp() {

        epic1 = new Epic("Эпик 1", "Описание 1", TaskStatus.NEW);
        subtask1 = new Subtask("Подзадача 1", "Описание 1", TaskStatus.NEW);
        task1 = new Task("Задача 1", "Описание 1", TaskStatus.IN_PROGRESS);
    }

    @Test
    void shouldSavePreviousTasksInHistory() {
        taskManager.createEpic(epic1);

        taskManager.createTask(task1);

        subtask1.setEpicId(epic1.getTaskId());
        taskManager.createSubtask(subtask1);

        taskManager.getEpicPerId(epic1.getTaskId());
        taskManager.getSubtaskPerId(subtask1.getTaskId());
        taskManager.getTaskPerId(task1.getTaskId());

        assertEquals(taskManager.getHistory().size(), 3, "В истории задач, должны сохранятся " +
                "все виды задач");

    }

}