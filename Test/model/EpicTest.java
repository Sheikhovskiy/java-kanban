package model;

import service.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    TaskManager taskManager = Managers.getDefaultTaskManager();

    private Epic epic1;
    private Epic epic2;
    private Subtask subtask1;

    @BeforeEach
     void setUp() {
        taskManager.deleteAllEpics();
        taskManager.deleteAllSubtasks();
        taskManager.deleteAllTasks();
        epic1 = new Epic("Эпик 1", "Описание 1", TaskStatus.NEW);
        epic2 = new Epic("Эпик 2", "Описание 2", TaskStatus.NEW);
        subtask1 = new Subtask("Подзадача 1", "Описание 1", TaskStatus.NEW);
    }

    @Test
    void shouldTwoEpicsBeEqualsIfSameId() {
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        epic1.setTaskId(1);
        epic2.setTaskId(1);
        assertEquals(epic1, epic2, "Два Эпика должны быть равны друг другу если равен их id.");
    }

    // Будет ошибка при компиляции так как в методе setSubtasks() тип аргумента - Subtask
/*    @Test
    void shouldEpicNotBeAddableAsHisOwnSubtask() {
        epic1.setSubtasks(epic2);

    }*/

    @Test
    void shouldSubtaskListBeEmptyIfWeDeleteAppropriateEpic() {
        taskManager.createEpic(epic1);
        int epicId = epic1.getTaskId();

        subtask1.setEpicId(epicId);
        taskManager.createSubtask(subtask1);
        taskManager.deleteEpicById(epicId);

        assertTrue(taskManager.printListOfAllSubtasks().isEmpty(), "При удаление Эпика, все соотвествующие " +
                "подзадачи удаляются");
    }

}