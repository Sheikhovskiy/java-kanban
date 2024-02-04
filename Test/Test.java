import model.*;
import service.InMemoryTaskManager;
import service.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

class Test {

    @org.junit.jupiter.api.Test
    void shouldDeleteTask() {
        TaskManager manager = new InMemoryTaskManager();
        Task task = new Task("TestDelete", "DescriptionDelete", TaskStatus.NEW);
        Task createdTask = manager.createTask(task);
        int taskId = createdTask.getTaskId();
        assertNotNull(manager.getTaskPerId(taskId));

        manager.deleteTaskById(taskId);
        assertNull(manager.getTaskPerId(taskId));
    }

    @org.junit.jupiter.api.Test
    void shouldUpdateTask() {
        TaskManager manager = new InMemoryTaskManager();
        Task task = new Task("BeforeUpdate", "BeforeUpdateDescription", TaskStatus.NEW);
        Task createdTask = manager.createTask(task);
        createdTask.setTaskName("AfterUpdate");
        createdTask.setTaskDescription("AfterUpdateDescription");
        createdTask.setStatus(TaskStatus.DONE);
        manager.taskUpdate(createdTask);

        Task updatedTask = manager.getTaskPerId(createdTask.getTaskId());
        assertEquals("AfterUpdate", updatedTask.getTaskName());
        assertEquals("AfterUpdateDescription", updatedTask.getTaskDescription());
        assertEquals(TaskStatus.DONE, updatedTask.getStatus());
    }

    @org.junit.jupiter.api.Test
    void shouldCalculateEpicStatus() {
        TaskManager manager = new InMemoryTaskManager();
        Epic epic = new Epic("EpicTest", "EpicDescription", TaskStatus.NEW);
        Epic createdEpic = manager.createEpic(epic);
        Subtask subtask1 = new Subtask(createdEpic.getTaskId(), "Subtask1", "Description1", TaskStatus.NEW);
        Subtask subtask2 = new Subtask(createdEpic.getTaskId(), "Subtask2", "Description2", TaskStatus.NEW);
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);

        // Переводим первую подзадачу в статус DONE
        subtask1.setStatus(TaskStatus.DONE);
        manager.subtaskUpdate(subtask1);

        // Переводим вторую подзадачу в статус IN_PROGRESS
        subtask2.setStatus(TaskStatus.IN_PROGRESS);
        manager.subtaskUpdate(subtask2);

        Epic updatedEpic = (Epic) manager.getEpicPerId(createdEpic.getTaskId());
        assertEquals(TaskStatus.IN_PROGRESS, updatedEpic.getStatus(), "Epic status should be IN_PROGRESS");

        // Переводим все подзадачи в статус DONE
        subtask1.setStatus(TaskStatus.DONE);
        manager.subtaskUpdate(subtask1);
        subtask2.setStatus(TaskStatus.DONE);
        manager.subtaskUpdate(subtask2);

        updatedEpic = (Epic) manager.getEpicPerId(createdEpic.getTaskId());
        assertEquals(TaskStatus.DONE, updatedEpic.getStatus(), "Epic status should be DONE after all subtasks are DONE");
    }

}