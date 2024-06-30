package service;

import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest extends TaskManagerTest {

    FileBackedTaskManager fileBackedTaskManagerWithFile;

    File tempFile;

    @Override
    protected TaskManager createTaskmanager() {
        try {
            tempFile = File.createTempFile("Temp_File", ".csv");
            return new FileBackedTaskManager(tempFile);

        } catch (IOException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    @BeforeEach
    void setUp() {

        super.setUp();

        fileBackedTaskManagerWithFile = (FileBackedTaskManager) createTaskmanager();


        epic1 = new Epic("Эпик 1", "Описание 1", 1);
        subtask1 = new Subtask(epic1.getTaskId(), "Подзадача 1", "Описание 1", TaskStatus.NEW);
        task1 = new Task("Задача 1", "Описание 1", TaskStatus.IN_PROGRESS);
        task2 = new Task("Задача 2", "Описание 2", TaskStatus.DONE);
        task3 = new Task("Задача 3", "Описание 3", TaskStatus.DONE);

    }



    @Test
    void shouldFileBeEmpty() {


        fileBackedTaskManagerWithFile.createTask(task1);
        fileBackedTaskManagerWithFile.createTask(task2);

        FileBackedTaskManager.loadFromFile(tempFile);
        assertEquals(0, tempFile.length());


    }


    @Test
    void shouldSaveMultipleTasks() {


        fileBackedTaskManagerWithFile.createTask(task1);
        fileBackedTaskManagerWithFile.createTask(task2);

        fileBackedTaskManagerWithFile.getTaskPerId(task1.getTaskId());
        fileBackedTaskManagerWithFile.getTaskPerId(task2.getTaskId());

        fileBackedTaskManagerWithFile.deleteTaskById(task1.getTaskId());

        assertEquals(1, fileBackedTaskManagerWithFile.tasks.size());


    }


/*    @Test
    void shouldLoadMultipleTasks() {
        fileBackedTaskManagerWithFile.createTask(task1);
        fileBackedTaskManagerWithFile.createTask(task2);
        fileBackedTaskManagerWithFile.createTask(task3);
        fileBackedTaskManagerWithFile.createEpic(epic1);

        fileBackedTaskManagerWithFile.getTaskPerId(task1.getTaskId());
        fileBackedTaskManagerWithFile.getTaskPerId(task2.getTaskId());
        fileBackedTaskManagerWithFile.getTaskPerId(task3.getTaskId());
        fileBackedTaskManagerWithFile.getEpicPerId(epic1.getTaskId());

        fileBackedTaskManagerWithFile.deleteEpicById(epic1.getTaskId());

        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);

        assertEquals(3, loadedManager.printListOfAllTasks().size() + loadedManager.printListOfAllEpics().size());
    }*/


/*    @Test
    void shouldLoadTaskWithDurationAndStartTime() {

        taskInstant1 = new Task("Временная задача", "Описание В. задачи", TaskStatus.NEW, 11, Instant.now().plusSeconds(10), 3 );
        taskInstant2 = new Task("Временная задача 2", "Описание В. задачи 2", TaskStatus.NEW, 12, Instant.now().plusSeconds(500), 7 );


        fileBackedTaskManagerWithFile.createTask(taskInstant1);
        fileBackedTaskManagerWithFile.createTask(taskInstant2);

*//*        fileBackedTaskManagerWithFile.getTaskPerId(taskInstant1.getTaskId());
        fileBackedTaskManagerWithFile.getTaskPerId(taskInstant2.getTaskId());*//*

        fileBackedTaskManagerWithFile.deleteTaskById(taskInstant2.getTaskId());


        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);

        assertEquals(1, loadedManager.printListOfAllTasks().size());


    }*/


/*    @Test
    void shouldLoadSubtaskWithDurationAndStartTime() {


        epic1 = new Epic("Эпик 1", "Описание 1",  1);
        fileBackedTaskManagerWithFile.createEpic(epic1);


        subtaskInstant1 = new Subtask("Временная Подзадача", "Описание В. задачи", TaskStatus.NEW,  Instant.now().plusSeconds(10), 3 );
        subtaskInstant2 = new Subtask("Временная Подзадача 2", "Описание В. задачи 2", TaskStatus.NEW,  Instant.now().plusSeconds(500), 7 );
        subtaskInstant1.setEpicId(epic1.getTaskId());
        subtaskInstant2.setEpicId(epic1.getTaskId());


        fileBackedTaskManagerWithFile.createSubtask(subtaskInstant1);
        fileBackedTaskManagerWithFile.createSubtask(subtaskInstant2);

        fileBackedTaskManagerWithFile.save();

        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);

        assertEquals(2, loadedManager.printListOfAllSubtasks().size());

    }*/




















}