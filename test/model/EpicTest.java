package model;

import service.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    TaskManager taskManager;

    private Epic epic1;
    private Epic epic2;

    @BeforeEach
     void setUp() {
        taskManager = Managers.getDefaultTaskManager();

        epic1 = new Epic("Эпик 1", "Описание 1", TaskStatus.NEW);
        epic2 = new Epic("Эпик 2", "Описание 2", TaskStatus.NEW);
    }

    @Test
    void shouldTwoEpicsBeEqualsIfSameId() {
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        epic1.setTaskId(1);
        epic2.setTaskId(1);
        assertEquals(epic1, epic2, "Два Эпика должны быть равны друг другу если равен их id.");
    }


    @Test
    void shouldEpicHaveSamePropertiesWhenCreated() {
        String expectedName = epic1.getTaskName();
        String expectedDescription = epic1.getTaskDescription();
        TaskStatus expectedStatus = epic1.getStatus();
        taskManager.createEpic(epic1);

        assertEquals(epic1.getTaskName(), expectedName, "Имя эпика должно сохранится при создании объекта");
        assertEquals(epic1.getTaskDescription(), expectedDescription, "Описание Эпики должно сохранится " +
                "при создании объекта");
        assertEquals(epic1.getStatus(), expectedStatus, "Статус эпика должно сохранится " +
                "при создании объекта" );

    }


}