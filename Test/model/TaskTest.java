package model;

import org.junit.jupiter.api.Test;
import service.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    HistoryManager historyManager = new InMemoryHistoryManager();
    TaskManager manager = new InMemoryTaskManager(historyManager);

    @Test
    public void shouldTaskBeEqualToTaskIfSameId() {
        Task task = new Task("Задача 1", "Описание 1", TaskStatus.NEW);
        Task taskExpected = new Task("Задача 2", "Описание 2", TaskStatus.NEW);
        assertEqualTask(taskExpected, task, "Две задачи с одинаковым id должны быть равны");

    }

    @Test
    public void shouldTaskBeEqualToSubtaskIfSameId() {
        Task task = new Task("Задача 1", "Описание 1", TaskStatus.NEW);
        Subtask subtask = new Subtask("Подзадача 1", "Описание 2", TaskStatus.NEW);
        assertEqualTask(task, subtask, "Задача и подзадача с одинаковым id должны быть равны");
    }


    private static void assertEqualTask(Task expected, Task actual, String message) {
        assertEquals(expected.getTaskId(), actual.getTaskId(), "id");
    }

    @Test
    void shouldAddHistory() {
        Task task = new Task("1 задача", "Описание 1-ой задачи", TaskStatus.NEW);

        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();

        assertNotNull(history, "Список последних 10-ти задач не пустой");
        assertEquals(1, history.size(), "В списке последних 10-ти задач - 1 задача");

    }





}