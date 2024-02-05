package service;
<<<<<<< HEAD
import model.Epic;
import model.Subtask;
import model.Task;
import java.util.ArrayList;
import model.TaskStatus;

public interface TaskManager {
=======

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

>>>>>>> 4c2c45b (Небольшие доработки)
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
        ArrayList<Subtask> getSubtaskPerEpic(Epic epic);

<<<<<<< HEAD
        ArrayList<Task> getHistory();

=======
        List<Task> getHistory();
>>>>>>> 4c2c45b (Небольшие доработки)

}
