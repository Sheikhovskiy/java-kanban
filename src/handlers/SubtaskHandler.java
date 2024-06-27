package handlers;

import OwnExceptions.NotFoundException;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import handlers.BaseHttpHandler;
import model.Subtask;
import model.Task;
import service.TaskManager;

public class SubtaskHandler extends BaseHttpHandler {

    private final Gson gson;

    public SubtaskHandler(TaskManager taskManager) {
        super(taskManager);
        this.gson = new Gson();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("Началась обработка /subtasks запроса от клиента");

        String method = httpExchange.getRequestMethod();
        String response;

        switch(method) {
            case "GET":
                response = handleGetRequest(httpExchange);
                break;
            case "POST":
                response = handlePostRequest(httpExchange);
                break;
            case "DELETE":
                response = handleDeleteRequest(httpExchange);
                break;
            default:
                response = "Некорректный метод !";
                sendNotFound(httpExchange, response);
        }

        if (!response.isEmpty()) {
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }

    }




    public String handleGetRequest(HttpExchange httpExchange) throws IOException {

        String response;
        int subtaskId = checkIfTaskHasId(httpExchange);

        if (subtaskId != -1) {

            try {
                Subtask subtaskFound = taskManager.getSubtaskPerId(subtaskId);
                response = toGson(subtaskFound);

            } catch (NotFoundException exception) {
                response = "Подзадача с таким id не найдена";
                sendNotFound(httpExchange, response);
            }

        } else {

            List<Task> subtaskListFound = taskManager.printListOfAllSubtasks();
            response = toGson(subtaskListFound);

            sendText(httpExchange, response);

        }
        return response;
    }


    public String handlePostRequest(HttpExchange httpExchange) throws IOException {

        String response;
        int subtaskId = checkIfTaskHasId(httpExchange);
        InputStream inputBodyStream = httpExchange.getRequestBody();
        String body = new String(inputBodyStream.readAllBytes(), StandardCharsets.UTF_8);

        Gson gson = new Gson();

        Subtask userSubtask = fromGson(body, Subtask.class);


        if (subtaskId != -1) {
             userSubtask.setTaskId(subtaskId);

            try {
                taskManager.subtaskUpdate(userSubtask);
                response = "Подзадача успешно обновлена";
                sendText(httpExchange, response);

            } catch (NotFoundException exception) {
                response = "Подзадача не найдена";
                sendNotFound(httpExchange, response);
            } catch (IllegalStateException exception) {
                response = "id Подзадачи пересекаются с уже существующей";
                sendInteractions(httpExchange, response);
            }



        } else {

            try {
                taskManager.createSubtask(userSubtask);
                response = "Подзадача успешно создана";
                sendText(httpExchange, response);

            } catch (IllegalStateException exception) {
                response = "id Подзадачи пересекаются с уже существующей";
                sendInteractions(httpExchange, response);
            }

        }
        return response;

    }

    public String handleDeleteRequest(HttpExchange httpExchange) throws IOException {

        String response;
        int substaskId = checkIfTaskHasId(httpExchange);


        if (substaskId != - 1) {

            try {
                taskManager.deleteSubtaskById(substaskId);
                response = "Подзадача успешно удалена";
                sendText(httpExchange, response);


            } catch (NotFoundException exception) {
                response = "Подзадача не найдена";
                sendNotFound(httpExchange, response);

            }
        }
        return "Вы указали неверный или несуществующий id";
    }

















}
