package handlers;

import OwnExceptions.NotFoundException;
import adapters.InstantAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.TaskManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import model.Task;

public class TaskHandler extends BaseHttpHandler implements HttpHandler {

    private final Gson gson;

    public TaskHandler(TaskManager taskManager) {
        super(taskManager);
        this.gson = new GsonBuilder().registerTypeAdapter(Instant.class, new InstantAdapter()).create();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("Началась обработка /tasks запроса от клиента");

        getClientMethod(httpExchange);

    }

    @Override
    protected String handleGetRequest(HttpExchange httpExchange) throws IOException {
        String response;
        int taskId = checkIfTaskHasId(httpExchange);
        if (taskId != -1) {
            Task task = taskManager.getTaskPerId(taskId);
            if (task != null) {
                response = toGson(task);
                sendResponse(httpExchange, 200, response);
            } else {
                response = "Задача с таким номером не существует";
                sendResponse(httpExchange, 404, response);
            }
        } else {
            List<Task> tasksFound = taskManager.printListOfAllTasks();
            response = toGson(tasksFound);
            sendResponse(httpExchange, 200, response);
        }
        return response;
    }

    @Override
    protected String handlePostRequest(HttpExchange httpExchange) throws IOException {

        String response;

        int taskId = checkIfTaskHasId(httpExchange);

        try (InputStream bodyInputStream = httpExchange.getRequestBody()) {
            String body = new String(bodyInputStream.readAllBytes(), StandardCharsets.UTF_8);

            Task taskUser = fromGson(body, Task.class);

            if (taskId != -1) {
                taskUser.setTaskId(taskId);
                try {
                    taskManager.taskUpdate(taskUser);
                    response = "Задача успешно обновлена";
                    sendResponse(httpExchange, 200, response); // Возвращаем 200 при обновлении
                } catch (NotFoundException exception) {
                    response = "Задача не найдена";
                    sendResponse(httpExchange, 404, response);
                } catch (IllegalStateException exception) {
                    response = "id Задачи пересекаются с уже существующей";
                    sendResponse(httpExchange, 409, response);
                }
            } else {

                try {
                    taskManager.createTask(taskUser);
                    response = "Задача успешно создана!";
                    sendResponse(httpExchange, 201, response); // Возвращаем 201 при создании
                } catch (IllegalStateException exception) {
                    response = "id Задачи пересекаются с уже существующей";
                    sendResponse(httpExchange, 409, response);
                }
            }

            return response;
        }
    }


    @Override
    protected String handleDeleteRequest(HttpExchange httpExchange) throws IOException {
        String response = "";
        int taskId = checkIfTaskHasId(httpExchange);
        if (taskId != -1) {
            try {
                taskManager.deleteTaskById(taskId);
                response = "Задача успешно удалена !";
                sendResponse(httpExchange, 200, response);
            } catch (NotFoundException exception) {
                response = "Задача с таким id не найдена";
                sendResponse(httpExchange, 404, response);
            }
        } else {
            sendResponse(httpExchange, 400, "Некорректный id задачи");
        }
        return response;
    }

//    private int checkIfTaskHasId(HttpExchange httpExchange) {
//        String path = httpExchange.getRequestURI().getPath();
//        String[] pathSplitted = path.split("/");
//        if (pathSplitted.length > 2) {
//            try {
//                return Integer.parseInt(pathSplitted[2]);
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid task ID format: " + pathSplitted[2]);
//            }
//        }
//        return -1;
//    }

//    private void sendResponse(HttpExchange httpExchange, int statusCode, String response) throws IOException {
//
//        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
//
//        httpExchange.sendResponseHeaders(statusCode, responseBytes.length);
//
//        try (OutputStream os = httpExchange.getResponseBody()) {
//            os.write(responseBytes);
//        }
//    }

//    private String toGson(Object object) {
//        return gson.toJson(object);
//    }
//
//    private Task fromGson(String json, Class<Task> clazz) {
//        return gson.fromJson(json, clazz);
//    }
}
