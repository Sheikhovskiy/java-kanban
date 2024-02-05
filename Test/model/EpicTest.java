package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {


    @Test
    void equalEpics() {
        Epic epic = new Epic();
        Epic epicExpected = new Epic();
        assertEqualsTask(epic, epicExpected, "Эпики должны быть совпадать.");
    }

    @Test
    void name() {
        ArrayList<Object> objects = new ArrayList<>();
        objects.add("Тест");
        assertEquals(new ArrayList<>(), objects, "Список должен быть пустой");
    }

    private static void assertEqualsTask(Task expected, Task actual, String message) {
        assertEquals(expected.getTaskId(), actual.getTaskId(), "id");
        assertEquals(expected.getTaskName(), actual.getTaskName(), "name");
    }




}