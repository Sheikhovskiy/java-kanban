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


    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("Началась обработка /prioritized запроса от клиента");

        getClientMethod(httpExchange);
    }


    @Override
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

}
