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
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {


    FileBackedTaskManager fileBackedTaskManager;
    TaskManager taskManager;

    private Epic epic1;
    private Subtask subtask1;
    private Task task1;
    private Task task2;


    @BeforeEach
    void setUp() {
        fileBackedTaskManager = Managers.getDefaultFileBackedManager();
        taskManager = Managers.getDefaultTaskManager();

        epic1 = new Epic("Эпик 1", "Описание 1", TaskStatus.NEW);
        subtask1 = new Subtask("Подзадача 1", "Описание 1", TaskStatus.NEW);
        task1 = new Task("Задача 1", "Описание 1", TaskStatus.IN_PROGRESS);
        task2 = new Task("Задача 2", "Описание 2", TaskStatus.IN_PROGRESS);
    }


/*    В первую очередь проверьте новые методы FileBackedTaskManager:
    сохранение и загрузку пустого файла;
    сохранение нескольких задач;
    загрузку нескольких задач.*/

    @Test
    void shouldFileBeEmpty() {

        try {
            File tempFile = File.createTempFile("Temp_File", ".csv");


            fileBackedTaskManager.createTask(task1);
            fileBackedTaskManager.createTask(task2);

            assertEquals(0, Files.size(Path.of(tempFile.toURI())));


        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }


//    @Test
//    void shouldSaveMultipleTasks() {
//
//        try {
//            File tempFile = File.createTempFile("Temp_File", ".csv");
//
//
//            fileBackedTaskManager.createTask(task1);
//            fileBackedTaskManager.createTask(task2);
//
//            fileBackedTaskManager.getTaskPerId(task1.getTaskId());
//            fileBackedTaskManager.getTaskPerId(task2.getTaskId());
//
//            fileBackedTaskManager.deleteTaskById(task1.getTaskId());
//
//            assertEquals(0, Files.size(Path.of(tempFile.toURI())));
//
//
//        } catch (IOException exception) {
//            exception.printStackTrace();
//        }
//
//    }






//    @Test
//    void shouldLoadMultipleTasks() {
//
//
//
//
//    }




}