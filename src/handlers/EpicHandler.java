package handlers;

import OwnExceptions.NotFoundException;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import handlers.BaseHttpHandler;
import model.Epic;
import model.Subtask;
import model.Task;
import service.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class EpicHandler extends BaseHttpHandler {

    private final Gson gson;

    public EpicHandler(TaskManager taskManager) {
        super(taskManager);
        this.gson = new Gson();
    }


    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("Началась обработка /epics запроса от клиента");

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

        String response = "";
        String receivedText = "";

        int epicId = checkIfTaskHasId(httpExchange);

        if (epicId != -1) {

            String path = httpExchange.getRequestURI().getPath();
            String[] pathSplitted = path.split("/");

            if (pathSplitted[3] != null) {

                try {
                    receivedText = String.valueOf(pathSplitted[3]);

                } catch (Throwable exception) {
                    System.out.println(exception.getMessage());
                }

                if (receivedText.equals("subtasks")) {

                    Epic epicFound = taskManager.getEpicPerId(epicId);
                    List<Subtask> epicSubtasksFound = taskManager.getSubtaskPerEpic(epicFound);
                    response = toGson(epicSubtasksFound);
                    sendText(httpExchange, response);


                }
            } else {

                try {
                    Epic epicFound = taskManager.getEpicPerId(epicId);
                    response = toGson(epicFound);
                    sendText(httpExchange, response);
                } catch (NotFoundException exception) {
                    response = "Эпик с таким id не найден";
                    sendNotFound(httpExchange, response);
                }
            }

        } else {
            List<Task> epicListFound = taskManager.printListOfAllEpics();
            response = toGson(epicListFound);
            sendText(httpExchange, response);

        }
        return response;

    }




    public String handlePostRequest(HttpExchange httpExchange) throws IOException {

        String response;

        InputStream inputBodyStream = httpExchange.getRequestBody();
        String body = new String(inputBodyStream.readAllBytes(), StandardCharsets.UTF_8);

        Gson gson = new Gson();

        Epic epic = fromGson(body, Epic.class);

        try {
            taskManager.createEpic(epic);
            response = "Эпик успешно создан";
            sendText(httpExchange, response);

        } catch (IllegalStateException exception) {
            response = "id Подзадачи пересекаются с уже существующей";
            sendInteractions(httpExchange, response);
        }
        return  response;
    }


    public String handleDeleteRequest(HttpExchange httpExchange) throws IOException {

        String response;
        int epicId = checkIfTaskHasId(httpExchange);

        if (epicId != -1) {

            try {
                taskManager.deleteEpicById(epicId);
                response = "Эпик с id " + epicId + " успешно удалён !";
                sendText(httpExchange, response);

            } catch (NotFoundException exception) {
                response = "Эпик с таким id не найден";
                sendNotFound(httpExchange, response);

            }

        } else {
            response = "Вы не указали id Эпика";
            sendNotFound(httpExchange, response);
        }
        return  response;
    }

}
