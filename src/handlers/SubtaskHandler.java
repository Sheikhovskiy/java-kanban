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

        getClientMethod(httpExchange);

    }


    @Override
    public String handleGetRequest(HttpExchange httpExchange) throws IOException {

        String response;
        int subtaskId = checkIfTaskHasId(httpExchange);

        if (subtaskId != -1) {

            try {
                Subtask subtaskFound = taskManager.getSubtaskPerId(subtaskId);

                response = toGson(subtaskFound);
                sendResponse(httpExchange, 200, response);

            } catch (NotFoundException exception) {
                response = "Подзадача с таким id не найдена";
                sendResponse(httpExchange, 404, response);
            }

        } else {
            List<Task> subtaskListFound = taskManager.printListOfAllSubtasks();

            response = toGson(subtaskListFound);
            sendResponse(httpExchange, 200, response);

        }
        return response;
    }


    @Override
    public String handlePostRequest(HttpExchange httpExchange) throws IOException {

        String response;

        int subtaskId = checkIfTaskHasId(httpExchange);

        try (InputStream inputBodyStream = httpExchange.getRequestBody()) {
            String body = new String(inputBodyStream.readAllBytes(), StandardCharsets.UTF_8);

            Gson gson = new Gson();

            Subtask userSubtask = fromGson(body, Subtask.class);


            if (subtaskId != -1) {
                userSubtask.setTaskId(subtaskId);

                try {
                    taskManager.subtaskUpdate(userSubtask);
                    response = "Подзадача успешно обновлена";
                    sendResponse(httpExchange, 201, response);

                } catch (NotFoundException exception) {
                    response = "Подзадача не найдена";
                    sendResponse(httpExchange, 404, response);
                } catch (IllegalStateException exception) {
                    response = "id Подзадачи пересекаются с уже существующей";
                    sendResponse(httpExchange, 406, response);
                }


            } else {

                try {
                    taskManager.createSubtask(userSubtask);
                    response = "Подзадача успешно создана";
                    sendResponse(httpExchange, 200, response);

                } catch (IllegalStateException exception) {
                    response = "id Подзадачи пересекаются с уже существующей";
                    sendResponse(httpExchange, 406, response);
                }
            }

            return response;
        }
    }

    @Override
    public String handleDeleteRequest(HttpExchange httpExchange) throws IOException {

        String response;
        int substaskId = checkIfTaskHasId(httpExchange);


        if (substaskId != - 1) {

            try {
                taskManager.deleteSubtaskById(substaskId);
                response = "Подзадача успешно удалена";
                sendResponse(httpExchange, 200, response);


            } catch (NotFoundException exception) {
                response = "Подзадача не найдена";
                sendResponse(httpExchange, 404, response);
            }
        }
        return "Вы указали неверный или несуществующий id";
    }


}
