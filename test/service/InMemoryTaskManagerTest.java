package service;

import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTest extends TaskManagerTest {



    @Override
    protected TaskManager createTaskmanager() {
        return new InMemoryTaskManager();
    }


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

    @Override
    @Test
    void shouldAddEpic() {
        taskManager.createEpic(epic1);
        assertFalse(taskManager.printListOfAllEpics().isEmpty(), "Эпик должен добавиться в список Эпиков");
    }
    @Override
    @Test
    void shouldAddSubtask() {
        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);

        assertFalse(taskManager.printListOfAllSubtasks().isEmpty(), "Подзадача должна добавиться в список " +
                "подзадач");
    }

    @Override
    @Test
    void shouldAddTask() {
        taskManager.createTask(task1);

        assertFalse(taskManager.printListOfAllTasks().isEmpty(), "Задача должна добавиться в список задач");
    }

    @Override
    @Test
        void shouldBePossibleToDeleteTaskPerId() {
        taskManager.createTask(task1);
        taskManager.createTask(task2);

        taskManager.deleteTaskById(task1.getTaskId());

        assertEquals(1,  taskManager.printListOfAllTasks().size(), "В список задач было добавлено " +
                "две задачи, мы удалили одну из них по его id. В списке должна остаться 1 задача");
    }



    @Override
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

    @Override
    @Test
    void shouldBePossibleToDeleteEpicPerId() {
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);

        taskManager.deleteEpicById(epic1.getTaskId());

        assertEquals(1, taskManager.printListOfAllEpics().size(), "В список эпиков было " +
                "добавлено два эпикка, мы удалили один из них по его id. В списке должен остаться 1 эпик");
    }



    @Override
    @Test
    void shouldBePossibleToGetSubtaskPerItsId() {
        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);

        int subtaskId = subtask1.getTaskId();
        Subtask subtaskReceived = taskManager.getSubtaskPerId(subtaskId);
        assertEquals(subtask1, subtaskReceived, "Должно быть возможно получить Подзадачу по id");
    }


    @Override
    @Test
    void shouldBePossibleToGetTaskPerItsId() {
        taskManager.createTask(task1);

        int taskId = task1.getTaskId();
        Task taskReceived = taskManager.getTaskPerId(taskId);
        assertEquals(task1, taskReceived, "Должно быть возможно получить задачу по id");
    }

    @Override
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


    @Override
    @Test
    void shouldTaskBeDifferentAfterUpdatingIt() {
        taskManager.createTask(task1);
        task1.setTaskId(1);
        task1 = task2;
        task2.setTaskId(1);
        taskManager.taskUpdate(task1);


        assertEquals(taskManager.printListOfAllTasks().getFirst(), task1);
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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


    @Override
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

    @Override
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


    @Override
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




    @Test
    void shouldReturnPrioritizedTasks() {

        taskInstant1 = new Task("Временная задача", "Описание В. задачи", TaskStatus.NEW, 11, Instant.now().plusSeconds(10), 3 );
        taskInstant2 = new Task("Временная задача 2", "Описание В. задачи 2", TaskStatus.NEW, 12, Instant.now().plusSeconds(500), 7 );

        taskManager.createTask(taskInstant1);
        taskManager.createTask(taskInstant2);

/*        taskManager.taskUpdate(taskInstant1);
        taskManager.taskUpdate(taskInstant2);*/
//        taskManager.getPrioritizedTasks();


        assertEquals(2, taskManager.getPrioritizedTasks().size());

    }

    @Test
    void shouldReturnPrioritizedSubtask() {

        subtaskInstant1 = new Subtask("Временная Подзадача", "Описание В. задачи", TaskStatus.NEW,  Instant.now().plusSeconds(10), 3 );
        subtaskInstant2 = new Subtask("Временная Подзадача 2", "Описание В. задачи 2", TaskStatus.NEW,  Instant.now().plusSeconds(500), 7 );

        subtaskInstant1.setEpicId(epic1.getTaskId());
        subtaskInstant2.setEpicId(epic1.getTaskId());

        taskManager.createEpic(epic1);

        taskManager.createSubtask(subtaskInstant1);
        taskManager.createSubtask(subtaskInstant2);


        assertEquals(2, taskManager.getPrioritizedTasks().size());

    }


    @Test
    void shouldEpicBeTheSumOfItsSubtasksDuration() {


        subtaskInstant1 = new Subtask("Временная Подзадача", "Описание В. задачи", TaskStatus.NEW,  Instant.now().plusSeconds(10), 3 );
        subtaskInstant2 = new Subtask("Временная Подзадача 2", "Описание В. задачи 2", TaskStatus.NEW,  Instant.now().plusSeconds(500), 7 );

        subtaskInstant1.setEpicId(epic1.getTaskId());
        subtaskInstant2.setEpicId(epic1.getTaskId());

        taskManager.createEpic(epic1);

        taskManager.createSubtask(subtaskInstant1);
        taskManager.createSubtask(subtaskInstant2);

        assertEquals(10, epic1.getDuration());

    }




}