package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import model.Task;
import service.Managers;
import service.TaskManager;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class PrioritizedHandler extends BaseHttpHandler {


    private final Gson gson;

    public PrioritizedHandler(TaskManager taskManager) {
        super(taskManager);
        this.gson = new Gson();
    }


    TaskManager taskManager = Managers.getDefaultTaskManager();


    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("Началась обработка /prioritized запроса от клиента");

        String response;

        List<Task> prioritizedTasks = taskManager.getPrioritizedTasks();


        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            response = handleGetRequest(httpExchange);
        } else {
            response = "Некорректный метод !";
            sendResponse(httpExchange, 404, response);
        }

    }



    public String handleGetRequest(HttpExchange httpExchange) throws IOException {

        String response;

        try {
            List<Task> prioritizedTask = taskManager.getPrioritizedTasks();
            response = toGson(prioritizedTask);
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
