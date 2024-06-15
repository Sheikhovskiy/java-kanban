package service;

import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class TaskManagerTest {


    protected  TaskManager taskManager;
    protected abstract TaskManager createTaskmanager();
    protected Epic epic1;
    protected Epic epic2;
    protected Subtask subtask1;
    protected Subtask subtask2;
    protected Task task1;
    protected Task task2;
    protected Task task3;

    protected Task taskInstant1;
    protected Task taskInstant2;
    protected Subtask subtaskInstant1;
    protected Subtask subtaskInstant2;


    @BeforeEach
    void setUp() {
        //
        taskManager = createTaskmanager();

        epic1 = new Epic("Эпик 1", "Описание 1", TaskStatus.NEW, 1);
        epic2 = new Epic("Эпик 2", "Описание 2", TaskStatus.IN_PROGRESS);
        subtask1 = new Subtask(epic1.getTaskId(), "Подзадача 1", "Описание 1", TaskStatus.NEW);
        subtask2 = new Subtask("Подзадача 2", "Описание 2", TaskStatus.IN_PROGRESS);
        task1 = new Task("Задача 1", "Описание 1", TaskStatus.IN_PROGRESS);
        task2 = new Task("Задача 2", "Описание 2", TaskStatus.DONE);
        task3 = new Task("Задача 3", "Описание 3", TaskStatus.DONE);
    }


    @Test
    void shouldAddEpic() {

    }

    @Test
    void shouldAddSubtask() {

    }

    @Test
    void shouldAddTask() {

    }

    @Test
    void shouldBePossibleToDeleteTaskPerId() {

    }



    @Test
    void shouldBePossibleToDeleteSubtaskPerId() {

    }

    @Test
    void shouldBePossibleToDeleteEpicPerId() {

    }



    @Test
    void shouldBePossibleToGetSubtaskPerItsId() {

    }


    @Test
    void shouldBePossibleToGetTaskPerItsId() {

    }

    @Test
    void shouldBePossibleToSetIdAndGenerateIdForTasks() {

    }

    @Test
    void shouldTaskBeDifferentAfterUpdatingIt() {

    }


    @Test
    void shouldSubtaskBeDifferentAfterUpdatingIt() {


    }

    @Test
    void shouldEpicBeDifferentAfterUpdatingIt() {


    }

    @Test
    void shouldSubtaskListBeEmptyIfWeDeleteAppropriateEpic() {

    }

    @Test
    void shouldChangeEpicStatusToDone() {

    }

    @Test
    void shouldChangeEpicStatusToInProgress () {

    }

    @Test
    void shouldReturnEpicSubtaskList() {


    }


    @Test
    void shouldDeleteTaskFromHistoryListWhenTaskDeleted() {

    }

    @Test
    void shouldDeleteSubtaskFromHistoryListWhenSubtaskDeleted() {

    }


    @Test
    void shouldDeleteEpicFromHistoryListWhenEpicDeleted() {

    }

}
