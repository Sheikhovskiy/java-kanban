import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import service.FileBackedTaskManager;
import service.Managers;
import service.TaskManager;

import java.io.File;

import static service.FileBackedTaskManager.loadFromFile;


public class Main {

    public static void main(String[] args) {

        loadFromFile(new File("Saved_Data.csv"));

        FileBackedTaskManager fileBackedTaskManager = Managers.getDefaultFileBackedManager();
        TaskManager taskManager = Managers.getDefaultTaskManager();

/*        Epic epic1;
        Subtask subtask1;
        Task task1;
        Task task2;
        Task task3;


        fileBackedTaskManager = Managers.getDefaultFileBackedManager();
        taskManager = Managers.getDefaultTaskManager();

        epic1 = new Epic("Эпик 1", "Описание 1", TaskStatus.NEW);
        subtask1 = new Subtask("Подзадача 1", "Описание 1", TaskStatus.NEW);
        task1 = new Task("Задача 1", "Описание 1", TaskStatus.IN_PROGRESS);
        task2 = new Task("Задача 2", "Описание 2", TaskStatus.IN_PROGRESS);
        task3 = new Task("Задача 3", "Описание 3", TaskStatus.IN_PROGRESS);

        fileBackedTaskManager.createTask(task1);
        fileBackedTaskManager.createTask(task2);
        fileBackedTaskManager.createTask(task3);
        fileBackedTaskManager.getTaskPerId(task1.getTaskId());
        fileBackedTaskManager.getTaskPerId(task2.getTaskId());
        fileBackedTaskManager.getTaskPerId(task3.getTaskId());

        fileBackedTaskManager.deleteTaskById(task1.getTaskId());*/
    }
}
