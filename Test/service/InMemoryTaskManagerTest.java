package service;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {


    private Epic epic1;
    private Subtask subtask1;
    private Task task1;
    private Task task2;

    TaskManager taskManager = Managers.getDefaultTaskManager();

    @BeforeEach
    void setUp() {
        taskManager.deleteAllTasks();
        taskManager.deleteAllSubtasks();
        taskManager.deleteAllEpics();

        epic1 = new Epic("Эпик 1", "Описание 1", TaskStatus.NEW);
        subtask1 = new Subtask("Подзадача 1", "Описание 1", TaskStatus.NEW);
        task1 = new Task("Задача 1", "Описание 1", TaskStatus.IN_PROGRESS);
        task2 = new Task("Задача 2", "Описание 2", TaskStatus.DONE);
    }


    @Test
    void shouldAddEpic() {
        taskManager.createEpic(epic1);
        assertFalse(taskManager.printListOfAllEpics().isEmpty(), "Эпик должен добавиться в список Эпиков");
    }

    @Test
    void shouldAddSubtask() {
        taskManager.createEpic(epic1);
        subtask1.setEpicId(epic1.getTaskId());
        taskManager.createSubtask(subtask1);

        assertFalse(taskManager.printListOfAllSubtasks().isEmpty(), "Подзадача должна добавиться в список " +
                "подзадач");
    }

    @Test
    void shouldAddTask() {
        taskManager.createTask(task1);

        assertFalse(taskManager.printListOfAllTasks().isEmpty(), "Задача должна добавиться в список задач");
    }

    @Test
    void shouldBePossibleToGetEpicPerItsId() {
        taskManager.createEpic(epic1);

        int epicId = epic1.getTaskId();
        Epic epicReceived = taskManager.getEpicPerId(epicId);
        assertEquals(epic1, epicReceived, "Должно быть возможно получить эпик по id");
    }
    @Test
    void shouldBePossibleToGetSubtaskPerItsId() {
        taskManager.createEpic(epic1);
        subtask1.setEpicId(epic1.getTaskId());
        taskManager.createSubtask(subtask1);

        int subtaskId = subtask1.getTaskId();
        Subtask subtaskReceived = taskManager.getSubtaskPerId(subtaskId);
        assertEquals(subtask1, subtaskReceived, "Должно быть возможно получить Подзадачу по id");
    }


    @Test
    void shouldBePossibleToGetTaskPerItsId() {
        taskManager.createTask(task1);

        int taskId = task1.getTaskId();
        Task taskReceived = taskManager.getTaskPerId(taskId);
        assertEquals(task1, taskReceived, "Должно быть возможно получить задачу по id");
    }

    @Test
    void shouldBePossibleToSetIdAndGenerateIdForTasks() {
        Task task3 = new Task("Задача 3", "Описание 3", TaskStatus.NEW, 1);
        taskManager.createTask(task3);
        int initializedId = task3.getTaskId();
        Task taskWithSettedId = taskManager.getTaskPerId(initializedId);

        assertFalse(taskManager.printListOfAllTasks().isEmpty(), "Задача с сгенерированым id добавляется");
        assertEquals(task3, taskWithSettedId, "Задача с сгенерированым id находится по id");

        //taskManager.deleteAllTasks();

        taskManager.createTask(task2);
        Task taskWithGeneratedId = taskManager.getTaskPerId(task2.getTaskId());

        assertEquals(taskManager.printListOfAllTasks().size(), 2, "Задача с заданным id добавляется");
        assertEquals(task2, taskWithGeneratedId, "Задача с заданным id находится по id");
    }



}