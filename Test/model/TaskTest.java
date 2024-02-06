package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    TaskManager taskManager = Managers.getDefaultTaskManager();

    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
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


}