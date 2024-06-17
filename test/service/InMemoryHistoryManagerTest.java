package service;

import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    TaskManager taskManager;

    private Epic epic1;
    private Subtask subtask1;
    private Task task1;
    private Task task2;


    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefaultTaskManager();

        epic1 = new Epic("Эпик 1", "Описание 1");
        subtask1 = new Subtask("Подзадача 1", "Описание 1", TaskStatus.NEW);
        task1 = new Task("Задача 1", "Описание 1", TaskStatus.IN_PROGRESS);
        task2 = new Task("Задача 2", "Описание 2", TaskStatus.IN_PROGRESS);
    }

    @Test
    void shouldSavePreviousTasksInHistory() {
        taskManager.createEpic(epic1);

        taskManager.createTask(task1);

        subtask1.setEpicId(epic1.getTaskId());
        taskManager.createSubtask(subtask1);

        taskManager.getEpicPerId(epic1.getTaskId());
        taskManager.getSubtaskPerId(subtask1.getTaskId());
        taskManager.getTaskPerId(task1.getTaskId());

        taskManager.createTask(task2);
        taskManager.getTaskPerId(task2.getTaskId());

        assertEquals(4, taskManager.getHistory().size(), "В истории задач, должны сохранятся " +
                "все виды задач");

    }

    @Test
    void shouldReturnEmptyStory() {
        taskManager.createTask(task1);

        taskManager.createSubtask(subtask1);

        taskManager.createEpic(epic1);


        assertEquals(0, taskManager.getHistory().size(),  "При отсутствие вызова метода, сохраняющегося историю, история должна оставаться пустой");

    }



    @Test
    void shouldHistoryNotContainDuplicates() {

        taskManager.createEpic(epic1);

        taskManager.createTask(task1);

        subtask1.setEpicId(epic1.getTaskId());
        taskManager.createSubtask(subtask1);

        taskManager.getEpicPerId(epic1.getTaskId());

        taskManager.getSubtaskPerId(subtask1.getTaskId());

        taskManager.getTaskPerId(task1.getTaskId());

        taskManager.createTask(task2);
        taskManager.getTaskPerId(task2.getTaskId());

        taskManager.getTaskPerId(task1.getTaskId());
        taskManager.getTaskPerId(task2.getTaskId());



        assertEquals(4, taskManager.getHistory().size(), "В истории сохраняются только уникальные задачи");
    }


    @Test
    void shouldBeAbleToDeleteFromHistory() {

        taskManager.createTask(task1);

        subtask1.setEpicId(epic1.getTaskId());
        taskManager.createSubtask(subtask1);

        taskManager.createTask(task2);

        taskManager.createEpic(epic1);

        taskManager.getTaskPerId(task1.getTaskId());
        taskManager.getTaskPerId(task2.getTaskId());
        taskManager.getSubtaskPerId(subtask1.getTaskId());
        taskManager.getEpicPerId(epic1.getTaskId());

        taskManager.deleteTaskById(task1.getTaskId());

        assertEquals(2, taskManager.getHistory().size());

    }



}