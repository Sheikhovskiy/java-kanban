package handlers;

import OwnExceptions.NotFoundException;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import handlers.BaseHttpHandler;
import service.Managers;
import service.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import model.Task;

public class TaskHandler extends BaseHttpHandler {

    private final Gson gson;
    private final TaskManager taskManager;

    public TaskHandler(TaskManager taskManager) {
        super(taskManager);
        this.taskManager = taskManager;
        this.gson = new Gson();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("Началась обработка /tasks запроса от клиента");

        TaskManager taskManager = Managers.getDefaultTaskManager();
        String response = "";
        String method = httpExchange.getRequestMethod();
        System.out.println(method);

        httpExchange.getRequestHeaders().forEach((key, value) ->
                System.out.println("Header: " + key + " Value: " + value));

        switch (method) {
            case "GET":
                response = handleGetRequest(httpExchange);
                break;
            case "POST":
                response = handlePostRequest(httpExchange);
                System.out.println("Response");
                break;
            case "DELETE":
                response = handleDeleteRequest(httpExchange);
                break;
            default:
                response = "Некорректный метод !";
                sendNotFound(httpExchange, response);

        }



        if (response.isEmpty()) {
            httpExchange.sendResponseHeaders(404, -1);
        } else {
            byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
            httpExchange.sendResponseHeaders(200, responseBytes.length);
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(responseBytes);
            }
        }
    }

    public String handleGetRequest(HttpExchange httpExchange) throws IOException {

        String response = "";
        TaskManager taskManager = Managers.getDefaultTaskManager();
        int taskId = checkIfTaskHasId(httpExchange);



        if (taskId != -1) {
            try {
                Task task = taskManager.getTaskPerId(taskId);
                if (task != null) {
                    response = toGson(task);
                    sendText(httpExchange, response);
                    //httpExchange.sendResponseHeaders(200, response.getBytes().length);
                } else {
                    response = "Задача с таким номером не существует";
                    sendNotFound(httpExchange, response);
                    //httpExchange.sendResponseHeaders(404, response.getBytes().length);
                }

            } catch (NullPointerException exc) {
                response = "Задача с таким номером не существует";
                sendNotFound(httpExchange, response);
                //httpExchange.sendResponseHeaders(404, response.getBytes().length);
            }
        } else {
            List<Task> tasks = taskManager.printListOfAllTasks();
            response = toGson(tasks);

            sendText(httpExchange, response);
            //httpExchange.sendResponseHeaders(200, response.getBytes().length);

        }
        return response;

    }


    public String handlePostRequest(HttpExchange httpExchange) throws IOException {

        InputStream bodyInputStream = httpExchange.getRequestBody();
        String body = new String(bodyInputStream.readAllBytes(), StandardCharsets.UTF_8);

        Task taskUser = fromGson(body, Task.class);

        System.out.println(taskUser);

        String response;
        int taskId = checkIfTaskHasId(httpExchange);

        if (taskId != -1) {
            taskUser.setTaskId(taskId);
            try {
                taskManager.taskUpdate(taskUser);
                response = "Задача успешно обновлена";
                httpExchange.sendResponseHeaders(200, response.getBytes().length);
            } catch (NotFoundException exception) {
                response = "Задача не найдена";
                httpExchange.sendResponseHeaders(404, response.getBytes(StandardCharsets.UTF_8).length);
            } catch (IllegalStateException exception) {
                response = "id Задачи пересекаются с уже существующей";
                httpExchange.sendResponseHeaders(409, response.getBytes(StandardCharsets.UTF_8).length);
            }
        } else {
            try {
                taskManager.createTask(taskUser);
                response = "Задача успешно создана!";
                httpExchange.sendResponseHeaders(201, response.getBytes(StandardCharsets.UTF_8).length);
            } catch (IllegalStateException exception) {
                response = "id Задачи пересекаются с уже существующей";
                httpExchange.sendResponseHeaders(409, response.getBytes(StandardCharsets.UTF_8).length);
            }
        }
        return response;
    }



    public String handleDeleteRequest(HttpExchange httpExchange) throws IOException {

        String response = "";
        TaskManager taskManager = Managers.getDefaultTaskManager();

        int taskId = checkIfTaskHasId(httpExchange);

        if (taskId != -1) {

            try {
                taskManager.deleteTaskById(taskId);
                response = "Задача успешно удалена !";

                sendText(httpExchange, response);
                //httpExchange.sendResponseHeaders(200, response.getBytes().length);

            } catch (NotFoundException exception) {
                response = "Задача с таким id не найдена";

                sendNotFound(httpExchange, response);
                //httpExchange.sendResponseHeaders(404, response.getBytes().length);
            }
        }

        return response;
    }

/*    public static int checkIfTaskHasId(HttpExchange httpExchange) throws IOException {

        int receivedId = -1;

        String path = httpExchange.getRequestURI().getPath();
        String[] pathSplitted = path.split("/");

        if (pathSplitted[2] != null) {

            try {
                receivedId = Integer.parseInt(pathSplitted[2]);

            } catch (NumberFormatException exc) {
                httpExchange.sendResponseHeaders(400, 0);
                System.out.println(exc.getMessage());
            }
        }
        return receivedId;
    }*/







}









