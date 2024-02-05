<<<<<<< HEAD
=======
import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import service.InMemoryTaskManager;
import service.Managers;
import service.TaskManager;

import java.util.ArrayList;


>>>>>>> 4c2c45b (Небольшие доработки)
public class Main {

    public static void main(String[] args) {

<<<<<<< HEAD
=======
        TaskManager taskManager = Managers.getDefaultTaskManager();






        /*        Task task1 = new Task("Первая задача", "Первая простая пробная задача", TaskStatus.NEW);
        Task task2 = new Task("Вторая задача", "Вторая простая задача", TaskStatus.NEW);
        Task task3 = new Task("Третья задача", "Третья простая задача", TaskStatus.NEW);

        Epic epic = new Epic();
        Epic epic1 = new Epic("Эпик", "Описание 1 Эпик", TaskStatus.NEW, 1);
        Epic epic2 = new Epic("Эпик", "Описание 2 Эпика", TaskStatus.NEW, 2);


        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        inMemoryTaskManager.createTask(task1);
        inMemoryTaskManager.createTask(task2);
        inMemoryTaskManager.createEpic(epic1);
        inMemoryTaskManager.createEpic(epic2);

        ArrayList<Subtask> subTasksList1 = new ArrayList<>();
        Subtask subtask1 = new Subtask(epic1.getTaskId(), "Первая подзадача", "Описание 1", TaskStatus.NEW);
        Subtask subtask2 = new Subtask(epic1.getTaskId(), "Вторая подзадача", "Описание 2", TaskStatus.NEW);

        ArrayList<Subtask> subsTasksList2 = new ArrayList<>();
        Subtask subtask3 = new Subtask(epic2.getTaskId(), "Третья подзадача", "Описание 3", TaskStatus.NEW);


        inMemoryTaskManager.createSubtask(subtask1);
        inMemoryTaskManager.createSubtask(subtask2);
        inMemoryTaskManager.createSubtask(subtask3);

        System.out.println(task1);
        System.out.println(task2);
        System.out.println(epic1);
        System.out.println(epic2);
        System.out.println(subtask1);
        System.out.println(subtask2);
        System.out.println(subtask3);
        System.out.println(inMemoryTaskManager.getSubtaskPerEpic(epic1));

        task1.setStatus(TaskStatus.DONE);
        task2.setStatus(TaskStatus.IN_PROGRESS);

        subtask1.setStatus(TaskStatus.DONE);
        subtask2.setStatus(TaskStatus.DONE);
        subtask3.setStatus(TaskStatus.IN_PROGRESS);

        inMemoryTaskManager.calculateStatus(epic1);
        inMemoryTaskManager.calculateStatus(epic2);
        inMemoryTaskManager.subtaskUpdate(subtask3);

        inMemoryTaskManager.deleteTaskById(task2.getTaskId());
        inMemoryTaskManager.deleteSubtaskById(subtask1.getTaskId());
        inMemoryTaskManager.deleteEpicById(epic1.getTaskId());*/

>>>>>>> 4c2c45b (Небольшие доработки)


    }
}
