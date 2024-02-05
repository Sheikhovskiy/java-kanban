package service;

import model.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    InMemoryTaskManager memoryTaskManager = new InMemoryTaskManager(new HistoryManager() {
        @Override
        public void add(Task task) {

        }

        @Override
        public List<Task> getAll() {
            return null;
        }
    });





}