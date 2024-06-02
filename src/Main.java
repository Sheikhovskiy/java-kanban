import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import service.FileBackedTaskManager;
import java.io.File;


public class Main {

    public static void main(String[] args) {

        File testedFile = new File("Saved_data.csv");
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(testedFile);

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

        fileBackedTaskManager.getTaskPerId(task1.getTaskId());
//        fileBackedTaskManager.getTaskPerId(task2.getTaskId());


        fileBackedTaskManager.deleteTaskById(task3.getTaskId());


        FileBackedTaskManager loadedFileTaskManager = FileBackedTaskManager.loadFromFile(testedFile);
        System.out.println(loadedFileTaskManager.printListOfAllTasks());
        System.out.println(loadedFileTaskManager.getHistory());


    }
}
