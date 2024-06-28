package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import model.Task;
import service.HistoryManager;
import service.Managers;
import service.TaskManager;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class HistoryHandler extends BaseHttpHandler {


    private final Gson gson;

    public HistoryHandler(TaskManager taskManager) {
        super(taskManager);
        this.gson = new Gson();
    }

    HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("Началась обработка /tasks запроса от клиента");

        List<Task> history = historyManager.getHistory();
        String response;

        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            response = handleGetRequest(httpExchange);
        } else {
            response = "Некорректный метод !";
            sendNotFound(httpExchange, response);
        }

    }


    public String handleGetRequest(HttpExchange httpExchange) throws IOException {

        String response;

        try {
            List<Task> tasksHistory = taskManager.getHistory();
            response = toGson(tasksHistory);
            sendResponse(httpExchange, 200, response);

        } catch (Throwable exception) {
            response = exception.getMessage();
            sendResponse(httpExchange, 404, response);
        }
        return response;
    }

    private void sendResponse(HttpExchange httpExchange, int statusCode, String response) throws IOException {

        byte[] responseInBytes = response.getBytes(StandardCharsets.UTF_8);

        httpExchange.sendResponseHeaders(statusCode, responseInBytes.length);

        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(responseInBytes);
        }

    }




}
