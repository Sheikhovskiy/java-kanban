package model;

import org.junit.jupiter.api.DisplayName;
import service.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    TaskManager taskManager;

    private Epic epic1;
    private Epic epic2;

    private Subtask subtask1;

    private Subtask subtask2;

    @BeforeEach
     void setUp() {
        taskManager = Managers.getDefaultTaskManager();

        epic1 = new Epic("Эпик 1", "Описание 1", TaskStatus.NEW);
        epic2 = new Epic("Эпик 2", "Описание 2", TaskStatus.NEW);
        subtask1 = new Subtask("Подзадача 1", "Описание 1", TaskStatus.NEW);
        subtask2 = new Subtask("Подзадача 2", "Описание 2", TaskStatus.NEW);

        taskManager.createEpic(epic1);

        subtask1.setEpicId(epic1.getTaskId());

    }

    @Test
    void shouldTwoEpicsBeEqualsIfSameId() {
        taskManager.createEpic(epic2);

        epic1.setTaskId(1);
        epic2.setTaskId(2);
        assertEquals(epic1, epic2, "Два Эпика должны быть равны друг другу если равен их id.");
    }


    @Test
    void shouldEpicHaveSamePropertiesWhenCreated() {
        String expectedName = epic1.getTaskName();
        String expectedDescription = epic1.getTaskDescription();
        TaskStatus expectedStatus = epic1.getStatus();

        assertEquals(epic1.getTaskName(), expectedName, "Имя эпика должно сохранится при создании объекта");
        assertEquals(epic1.getTaskDescription(), expectedDescription, "Описание Эпики должно сохранится " +
                "при создании объекта");
        assertEquals(epic1.getStatus(), expectedStatus, "Статус эпика должно сохранится " +
                "при создании объекта" );

    }

    @DisplayName("Все подзадачи под статусом NEW - Эпик со статусом NEW")
    @Test
    void shouldEpicBeNewIfSubtasksAreNew() {


        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        subtask2.setEpicId(epic1.getTaskId());

        assertEquals(epic1.getStatus(), TaskStatus.NEW);
    }


    @DisplayName("Все подзадачи под статусом DONE - Эпик со статусом DONE")
    @Test
    void shouldEpicBeDoneIfSubtasksAreDone() {

        subtask1.setStatus(TaskStatus.DONE);
        subtask2.setStatus(TaskStatus.DONE);

        subtask2.setEpicId(epic1.getTaskId());



        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);



        assertEquals(epic1.getStatus(), TaskStatus.DONE);

    }


    @DisplayName("Подзадачи под статусом NEW и DONE - Эпик со статусом NEW")
    @Test
    void shouldEpicBeNewIfSubtasksAreDoneAndNew() {

        subtask2.setStatus(TaskStatus.NEW);

        subtask2.setEpicId(epic1.getTaskId());

        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        assertEquals(epic1.getStatus(), TaskStatus.NEW);

    }


    @DisplayName("Подзадачи под статусом IN_PROGRESS - Эпик со статусом IN_PROGRESS")
    @Test
    void shouldEpicBeInProgressIfSubtasksAreInProgress() {

        subtask1.setStatus(TaskStatus.IN_PROGRESS);
        subtask2.setStatus(TaskStatus.IN_PROGRESS);

        subtask2.setEpicId(epic1.getTaskId());

        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        assertEquals(epic1.getStatus(), TaskStatus.IN_PROGRESS);
    }


}