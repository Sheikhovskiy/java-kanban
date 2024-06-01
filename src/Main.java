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

//        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(new File("Saved_data.csv"));
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(new File("Saved_data.csv"));

         Epic epic1;
         Subtask subtask1;
         Task task1;
         Task task2;
         Task task3;

        task1 = new Task("Задача 1", "Описание 1", TaskStatus.IN_PROGRESS);
        task2 = new Task("Задача 2", "Описание 2", TaskStatus.IN_PROGRESS);
        task3 = new Task("Задача 3", "Описание 3", TaskStatus.IN_PROGRESS);


        fileBackedTaskManager.createTask(task1);
        fileBackedTaskManager.createTask(task2);
        fileBackedTaskManager.createTask(task3);

        fileBackedTaskManager.deleteTaskById(task1.getTaskId());




    }
}
