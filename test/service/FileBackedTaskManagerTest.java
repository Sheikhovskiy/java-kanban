package service;

import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {


    FileBackedTaskManager fileBackedTaskManager;
    FileBackedTaskManager fileBackedTaskManagerWithFile;
    TaskManager taskManager;

    File tempFile;

    private Epic epic1;
    private Subtask subtask1;
    private Task task1;
    private Task task2;
    private Task task3;


    @BeforeEach
    void setUp() {
        fileBackedTaskManager = Managers.getDefaultFileBackedManager();
        taskManager = Managers.getDefaultTaskManager();

        try {
            tempFile = File.createTempFile("Temp_File", ".csv");
            fileBackedTaskManagerWithFile = new FileBackedTaskManager(tempFile);

        } catch (IOException exception){
            System.out.println(exception.getMessage());
        }

        epic1 = new Epic("Эпик 1", "Описание 1", TaskStatus.NEW);
        subtask1 = new Subtask("Подзадача 1", "Описание 1", TaskStatus.NEW);
        task1 = new Task("Задача 1", "Описание 1", TaskStatus.IN_PROGRESS);
        task2 = new Task("Задача 2", "Описание 2", TaskStatus.IN_PROGRESS);
        task3 = new Task("Задача 3", "Описание 3", TaskStatus.IN_PROGRESS);
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


    @Test
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

        FileBackedTaskManager.loadFromFile(tempFile);

        assertEquals(3, fileBackedTaskManagerWithFile.tasks.size() + fileBackedTaskManager.epics.size());
    }



}