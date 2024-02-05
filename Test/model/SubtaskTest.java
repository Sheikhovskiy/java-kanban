package model;

import org.junit.jupiter.api.Test;
import service.InMemoryTaskManager;
import service.Managers;
import service.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    @Test
    public void shouldNotEpicBeAddableToSubtask() {
        Epic epic = new Epic("Эпик", "Описание эпика 1", TaskStatus.NEW);
        Subtask subtask = new Subtask("Сабтаск", "Описание сабтаска 1", TaskStatus.NEW);

        epic.setTaskId(subtask.getTaskId());


        //assertEquals();
    }

    @Test
    public void shouldSubtaskNotBeHisOwnEpic() {
        TaskManager manager = Managers.getDefaultTaskManager();
        Epic epic = new Epic("Эпик 1", "Описание эпика 1", TaskStatus.NEW);
        Subtask subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", TaskStatus.NEW);
        epic.setTaskId(1);
        manager.createEpic(epic);
        subtask.setEpicId(1);
        manager.createSubtask(subtask);
        assertNull(subtask, "Подзадача не может быть того же ID что и её эпик");
    }

}