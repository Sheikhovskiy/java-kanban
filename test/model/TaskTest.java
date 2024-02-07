package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    TaskManager taskManager;

    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefaultTaskManager();

        task1 = new Task("Задача 1", "Описание 1", TaskStatus.NEW);
        task2 = new Task("Задача 2", "Описание 2", TaskStatus.NEW);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
    }

    @Test
    void shouldTwoTasksBeEqualsIfSameId() {
        task1.setTaskId(1);
        task2.setTaskId(1);
        System.out.println(task1.getTaskId());
        System.out.println(task2.getTaskId());
        assertEquals(task1, task2, "Две задачи должны быть равны друг другу если равен их id.");
    }

    @Test
    void shouldTaskHaveSamePropertiesWhenCreated() {

        int expectedTaskId = 1;
        String expectedTaskName = task1.getTaskName();
        String expectedTaskDescription = task1.getTaskDescription();
        TaskStatus expectedTaskStatus = task1.getStatus();
        taskManager.createTask(task1);
        task1.setTaskId(1);

        assertEquals(task1.getTaskId(), expectedTaskId, "id задачи должен сохранится при создании объекта");
        assertEquals(task1.getTaskName(), expectedTaskName, "Описание задачи должно сохранится при создании " +
                "объекта");
        assertEquals(task1.getTaskDescription(), expectedTaskDescription, "Описание задачи должно сохранится " +
                "при создании объекта");
        assertEquals(task1.getStatus(), expectedTaskStatus, "Статус задачи должен сохранится при " +
                "создании объекта");

    }


}