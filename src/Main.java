import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import service.TaskManager;

import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {
        Task task1 = new Task("Первая задача", "Первая простая пробная задача", TaskStatus.NEW);
        Task task2 = new Task("Вторая задача", "Вторая простая задача", TaskStatus.NEW);
        Task task3 = new Task("Третья задача", "Третья простая задача", TaskStatus.NEW);

        ArrayList<Subtask> subTasksList1 = new ArrayList<>();
        Subtask subtask1 = new Subtask("Первая подзадача", "Описание 1", TaskStatus.NEW);
        Subtask subtask2 = new Subtask("Вторая подзадача", "Описание 2", TaskStatus.NEW);
        subTasksList1.add(subtask1);
        subTasksList1.add(subtask2);

        ArrayList<Subtask> subsTasksList2 = new ArrayList<>();
        Subtask subtask3 = new Subtask("Третья подзадача", "Описание 3", TaskStatus.NEW);
        subsTasksList2.add(subtask3);

        Epic epic = new Epic();
        Epic epic1 = new Epic("Эпик", "Описание 1 Эпик", TaskStatus.NEW, subTasksList1);
        Epic epic2 = new Epic("Эпик", "Описание 2 Эпика", TaskStatus.NEW, subsTasksList2);

        subtask1.setEpic(epic1);
        subtask2.setEpic(epic1);
        subtask3.setEpic(epic2);

        TaskManager taskManager = new TaskManager();

        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);

        System.out.println(task1);
        System.out.println(task2);
        System.out.println(epic1);
        System.out.println(epic2);
        System.out.println(subtask1);
        System.out.println(subtask2);
        System.out.println(subtask3);

        task1.setStatus(TaskStatus.DONE);
        task2.setStatus(TaskStatus.IN_PROGRESS);

        subtask1.setStatus(TaskStatus.DONE);
        subtask2.setStatus(TaskStatus.DONE);
        subtask3.setStatus(TaskStatus.IN_PROGRESS);

        taskManager.calculateStatus(epic1);
        taskManager.calculateStatus(epic2);
        taskManager.printListOfAllTasks();
        taskManager.printListOfAllSubtasks();
        taskManager.printListOfAllEpic();

        taskManager.taskDeleteId(task1.getTaskId());
        taskManager.epicDeleteId(epic1.getTaskId());
        taskManager.printListOfAllTasks();
        taskManager.printListOfAllEpic();












    }
}
