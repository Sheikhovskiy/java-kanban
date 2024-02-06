package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Managers;
import service.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    TaskManager taskManager = Managers.getDefaultTaskManager();

    private Subtask subtask1;
    private Subtask subtask2;


    @BeforeEach
    void setUp() {
        taskManager.deleteAllEpics();
        taskManager.deleteAllSubtasks();
        taskManager.deleteAllEpics();
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
    void shouldNotItsOwnIdBeHisEpicId() {
        subtask1.setTaskId(1);
        subtask1.setEpicId(subtask1.getTaskId());
        taskManager.createSubtask(subtask1);

        assertNull(taskManager.getEpicPerId(subtask1.getEpicId()), "Подзадача не может использовать свой id как" +
                "id своего эпика");

    }

}