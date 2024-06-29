import com.google.gson.Gson;
import model.Epic;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import service.InMemoryTaskManager;
import service.TaskManager;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HttpTaskManagerTasksTest {

    TaskManager manager = new InMemoryTaskManager();
    private static final int PORT = 8080;
    HttpTaskServer taskServer;
    Gson gson = HttpTaskServer.getGson();


    @BeforeEach
    public void setUp() throws IOException {
        manager.deleteAllTasks();
        manager.deleteAllSubtasks();
        manager.deleteAllEpics();
        taskServer = new HttpTaskServer(manager, PORT);
        taskServer.start();
    }

    @AfterEach
    public void shutDown() {
        taskServer.stop();
    }


    @Test
    public void shouldAddTask() throws IOException, InterruptedException {

        Task task = new Task("Test 2", "Testing task 2", TaskStatus.NEW, 2, Instant.now(), 5);

        // конвертируем её в JSON
        String taskJson = gson.toJson(task);
        System.out.println(taskJson);

        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .header("Content-Type", "application/json")
                .build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(201, response.statusCode());

        // проверяем, что создалась одна задача с корректным именем
        List<Task> tasksFromManager = manager.printListOfAllTasks();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Test 2", tasksFromManager.get(0).getTaskName(), "Некорректное имя задачи");
    }

    @Test
    public void shouldAddEpic() throws IOException, InterruptedException {

        Epic epic = new Epic("Epic 1", "Testing epic 1", 2, Instant.now(), 5);

        // конвертируем её в JSON
        String epicJson = gson.toJson(epic);
        System.out.println(epic);

        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder().uri(url)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(epicJson))
                .header("Content-Type", "application/json")
                .build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(201, response.statusCode());

        // проверяем, что создалась одна задача с корректным именем
        List<Task> tasksFromManager = manager.printListOfAllEpics();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        //assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Test 2", tasksFromManager.get(0).getTaskName(), "Некорректное имя задачи");
    }
}
