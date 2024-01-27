import service.TaskManager;
import model.Task;
import java.util.ArrayList;
import java.util.HashMap;
public class Main {

    public static void main(String[] args) {
        //Task task = new Task();

        TaskManager taskManager = new TaskManager();
        Task task = taskManager.createTask(new Task("Новая задача"));
        System.out.println("Create task: " + task);

        Task taskFromManager = taskManager.get(task.getTaskId());
        System.out.println("get task: " + taskFromManager);

        taskFromManager.setName("New name");
        taskManager.taskUpdate(taskFromManager);
        System.out.println("Update task: " + taskFromManager);

        taskManager.taskDeleteId(taskFromManager.getTaskId());
        System.out.println("Delete: " + task);

        // Тоже самое для Эпика и Подзадачи

    }


















/*
    private static void printMenu(){
        System.out.println("Выберите команду:");
        System.out.println("1 - Получить список всех задач");
        System.out.println("2 - Удалить все задачи");
        System.out.println("3 - Получить задачу по идентификатору");
        System.out.println("4 - Создать новую задачу");
        System.out.println("5 - Обновить задачу"); // Статус???
        System.out.println("6 - Удалить задачу по идентификатору");
        // Мб необязательно
        System.out.println("7 - Получения списка всех подзадач определённого эпика");


    }
*/



}
