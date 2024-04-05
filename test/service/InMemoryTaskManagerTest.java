package service;

import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {


    TaskManager taskManager;
    private Epic epic1;
    private Epic epic2;
    private Subtask subtask1;
    private Subtask subtask2;
    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefaultTaskManager();

        epic1 = new Epic("Эпик 1", "Описание 1", TaskStatus.NEW, 1);
        epic2 = new Epic("Эпик 2", "Описание 2", TaskStatus.IN_PROGRESS);
        subtask1 = new Subtask(epic1.getTaskId(), "Подзадача 1", "Описание 1", TaskStatus.NEW);
        subtask2 = new Subtask("Подзадача 2", "Описание 2", TaskStatus.IN_PROGRESS);
        task1 = new Task("Задача 1", "Описание 1", TaskStatus.IN_PROGRESS);
        task2 = new Task("Задача 2", "Описание 2", TaskStatus.DONE);
    }


    @Test
    void shouldAddEpic() {
        taskManager.createEpic(epic1);
        assertFalse(taskManager.printListOfAllEpics().isEmpty(), "Эпик должен добавиться в список Эпиков");
    }

    @Test
    void shouldAddSubtask() {
        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);

        assertFalse(taskManager.printListOfAllSubtasks().isEmpty(), "Подзадача должна добавиться в список " +
                "подзадач");
    }

    @Test
    void shouldAddTask() {
        taskManager.createTask(task1);

        assertFalse(taskManager.printListOfAllTasks().isEmpty(), "Задача должна добавиться в список задач");
    }

    @Test
        void shouldBePossibleToDeleteTaskPerId() {
        taskManager.createTask(task1);
        taskManager.createTask(task2);

        taskManager.deleteTaskById(task1.getTaskId());

        assertEquals(1,  taskManager.printListOfAllTasks().size(), "В список задач было добавлено " +
                "две задачи, мы удалили одну из них по его id. В списке должна остаться 1 задача");
    }



    @Test
    void shouldBePossibleToDeleteSubtaskPerId() {
        taskManager.createEpic(epic1);
        subtask2.setEpicId(epic1.getTaskId());
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);


        taskManager.deleteSubtaskById(subtask1.getTaskId());

        assertEquals(1, taskManager.printListOfAllSubtasks().size(), "В список подзадач было " +
                "добавлено две подзадачи, мы удалили одну из них по его id. В списке должна остаться 1 подзадача");
    }

    @Test
    void shouldBePossibleToDeleteEpicPerId() {
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);

        taskManager.deleteEpicById(epic1.getTaskId());

        assertEquals(1, taskManager.printListOfAllEpics().size(), "В список эпиков было " +
                "добавлено два эпикка, мы удалили один из них по его id. В списке должен остаться 1 эпик");
    }



    @Test
    void shouldBePossibleToGetSubtaskPerItsId() {
        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);

        int subtaskId = subtask1.getTaskId();
        Subtask subtaskReceived = taskManager.getSubtaskPerId(subtaskId);
        assertEquals(subtask1, subtaskReceived, "Должно быть возможно получить Подзадачу по id");
    }


    @Test
    void shouldBePossibleToGetTaskPerItsId() {
        taskManager.createTask(task1);

        int taskId = task1.getTaskId();
        Task taskReceived = taskManager.getTaskPerId(taskId);
        assertEquals(task1, taskReceived, "Должно быть возможно получить задачу по id");
    }

    @Test
    void shouldBePossibleToSetIdAndGenerateIdForTasks() {
        Task task3 = new Task("Задача 3", "Описание 3", TaskStatus.NEW, 1);
        taskManager.createTask(task3);
        int initializedId = task3.getTaskId();
        Task taskWithSettedId = taskManager.getTaskPerId(initializedId);

        assertFalse(taskManager.printListOfAllTasks().isEmpty(), "Задача с сгенерированым id добавляется");
        assertEquals(task3, taskWithSettedId, "Задача с сгенерированым id находится по id");

        taskManager.createTask(task2);
        Task taskWithGeneratedId = taskManager.getTaskPerId(task2.getTaskId());

        assertEquals(taskManager.printListOfAllTasks().size(), 2, "Задача с заданным id добавляется");
        assertEquals(task2, taskWithGeneratedId, "Задача с заданным id находится по id");
    }

    @Test
    void shouldTaskBeDifferentAfterUpdatingIt() {
        taskManager.createTask(task1);
        task1.setTaskId(1);
        task1 = task2;
        task2.setTaskId(1);
        taskManager.taskUpdate(task1);


        assertEquals(taskManager.printListOfAllTasks().getFirst(), task1);
    }


    @Test
    void shouldSubtaskBeDifferentAfterUpdatingIt() {
        taskManager.createEpic(epic1);

        taskManager.createSubtask(subtask1);

        subtask2.setEpicId(subtask1.getEpicId());
        subtask2.setTaskId(subtask1.getTaskId());
        subtask2.setTaskName("Новое имя");
        subtask2.setTaskDescription("Новое описание");
        subtask2.setStatus(TaskStatus.DONE);


        taskManager.subtaskUpdate(subtask2);

        assertEquals(taskManager.printListOfAllSubtasks().getFirst(), subtask2);

    }

    @Test
    void shouldEpicBeDifferentAfterUpdatingIt() {
        taskManager.createEpic(epic1);

        epic2.setTaskId(epic1.getTaskId());
        epic2.setTaskName("Новое имя");
        epic2.setTaskDescription("Новое описание");
        epic2.setStatus(TaskStatus.DONE);
        taskManager.epicUpdate(epic2);


        assertEquals(taskManager.printListOfAllEpics().getFirst(), epic2);

    }

    @Test
    void shouldSubtaskListBeEmptyIfWeDeleteAppropriateEpic() {
        taskManager.createEpic(epic1);
        int epicId = epic1.getTaskId();

        subtask1.setEpicId(epicId);
        taskManager.createSubtask(subtask1);
        taskManager.deleteEpicById(epicId);

        assertTrue(taskManager.printListOfAllSubtasks().isEmpty(), "При удаление Эпика, все соотвествующие " +
                "подзадачи удаляются");
    }

    @Test
    void shouldChangeEpicStatusToDone() {
        taskManager.createEpic(epic1);
        subtask2.setEpicId(epic1.getTaskId());

        subtask1.setStatus(TaskStatus.DONE);
        subtask2.setStatus(TaskStatus.DONE);

        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        assertEquals(TaskStatus.DONE, epic1.getStatus(), "При смене статус у подзадач эпика, обновляется и " +
                "высчитывается новый статус эпика. Если все подзадачи имеют статус ЗАВЕРШЕН, то и эпик соответсвенно");
    }

    @Test
    void shouldChangeEpicStatusToInProgress () {
        taskManager.createEpic(epic1);
        subtask1.setEpicId(epic1.getTaskId());
        subtask2.setEpicId(epic1.getTaskId());

        subtask2.setStatus(TaskStatus.DONE);
        subtask1.setStatus(TaskStatus.IN_PROGRESS);

        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        assertEquals(TaskStatus.IN_PROGRESS, epic1.getStatus(), "При смене статус у подзадач эпика, " +
                " обновляется и высчитывается новый статус эпика. Если все подзадачи имеют статус в прогрессе, то и " +
                "эпик соответсвенно");
    }

    @Test
    void shouldReturnEpicSubtaskList() {
        taskManager.createEpic(epic1);
        int epicId = epic1.getTaskId();

        subtask1.setEpicId(epicId);
        subtask2.setEpicId(epicId);

        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        assertFalse(taskManager.getSubtaskPerEpic(epic1).isEmpty(), "По Эпику можно получить список " +
                "его подзадач");

    }


    @Test
    void shouldDeleteTaskFromHistoryListWhenTaskDeleted() {
        taskManager.createTask(task1);
        taskManager.createTask(task2);

        int taskTested = task1.getTaskId();

        taskManager.getTaskPerId(task2.getTaskId());
        taskManager.getTaskPerId(taskTested);

        taskManager.deleteTaskById(taskTested);


        assertEquals(1, taskManager.getHistory().size());
    }

    @Test
    void shouldDeleteSubtaskFromHistoryListWhenSubtaskDeleted() {
        taskManager.createEpic(epic1);
        subtask2.setEpicId(epic1.getTaskId());

        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        int taskTested = subtask2.getTaskId();

        taskManager.getSubtaskPerId(subtask1.getTaskId());
        taskManager.getSubtaskPerId(taskTested);

        taskManager.deleteSubtaskById(taskTested);


        assertEquals(1, taskManager.getHistory().size());
    }


    @Test
    void shouldDeleteEpicFromHistoryListWhenEpicDeleted() {
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);

        int taskTested = epic1.getTaskId();

        taskManager.getEpicPerId(epic2.getTaskId());
        taskManager.getEpicPerId(taskTested);

        taskManager.deleteEpicById(taskTested);


        assertEquals(1, taskManager.getHistory().size());
    }

}