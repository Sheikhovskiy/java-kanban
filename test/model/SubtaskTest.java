package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Managers;
import service.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    TaskManager taskManager;

    private Subtask subtask1;
    private Subtask subtask2;
    private Epic epic1;


    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefaultTaskManager();

        epic1 = new Epic("Эпик 1", "Описание 1", TaskStatus.NEW);
        subtask1 = new Subtask("Подзадача 1", "Описание 1", TaskStatus.NEW);
        subtask2 = new Subtask("Подзадача 2", "Описание 2", TaskStatus.NEW);
    }

    @Test
    void shouldTwoSubtasksBeEqualsIfSameId() {
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        subtask1.setTaskId(1);
        subtask1.setTaskId(2);
        assertEquals(subtask1, subtask2, "Две подзадачи должны быть равны друг другу если равен их id.");
    }

    @Test
    void shouldSubtaskHaveSamePropertiesWhenCreated() {
        int expectedSubtaskId = 2;
        String expectedSubtaskName = subtask1.getTaskName();
        String expectedSubtaskDescription = subtask1.getTaskDescription();
        TaskStatus expectedSubtaskStatus = subtask1.getStatus();
        taskManager.createEpic(epic1);
        int epicId = epic1.getTaskId();

        subtask1.setTaskId(2);
        subtask1.setEpicId(epic1.getTaskId());
        taskManager.createSubtask(subtask1);



        assertEquals(subtask1.getTaskId(), expectedSubtaskId, "id подзадачи должен сохранится при " +
                " создании объекта");
        assertEquals(subtask1.getEpicId(), epicId, "id эпика подзадачи должно сохранится при создание объекта" +
                "id своего эпика");
        assertEquals(subtask1.getTaskName(), expectedSubtaskName, "Имя подзадачи должно сохранится при " +
                "создании объекта");
        assertEquals(subtask1.getTaskDescription(), expectedSubtaskDescription, "Описание подзадачи должно" +
                "сохранится при создании объекта");
        assertEquals(subtask1.getStatus(), expectedSubtaskStatus, "Статус подзадачи должно сохранится при " +
                "создании объекта ");


    }



}