package service;
import model.Epic;
import model.Subtask;
import model.Task;
import java.util.List;


public interface TaskManager {

        boolean checkTaskTime(Task task);

        List<Task> getPrioritizedTasks();

        List<Task> printListOfAllTasks();

        List<Task> printListOfAllSubtasks();

        List<Task> printListOfAllEpics();

        void deleteAllTasks();

        void deleteAllSubtasks();

        void deleteAllEpics();

        Task getTaskPerId(int id);

        Subtask getSubtaskPerId(int id);

        Epic getEpicPerId(int id);

        Task createTask(Task task);

        Subtask createSubtask(Subtask subtask);

        Epic createEpic(Epic epic);

        void taskUpdate(Task task);

        void epicUpdate(Epic epic);

        void subtaskUpdate(Subtask subtask);

        void deleteTaskById(int taskId);

        void deleteSubtaskById(int subtaskId);

        void deleteEpicById(int epicId);

        List<Subtask> getSubtaskPerEpic(Epic epic);

        List<Task> getHistory();
}
