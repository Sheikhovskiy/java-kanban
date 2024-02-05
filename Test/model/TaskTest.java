package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {


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


    private static <T extends Task> void assertEqualTask(Task expected, T actual, String message) {
        assertEquals(expected.getTaskId(), actual.getTaskId(), "id");
    }

}