package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import handlers.BaseHttpHandler;
import model.Task;
import service.Managers;
import service.TaskManager;

import java.io.IOException;
import java.io.OutputStream;
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

        try {
            List<Task> prioritizedTask = taskManager.getPrioritizedTasks();
            response = toGson(prioritizedTask);
            sendText(httpExchange, response);

        } catch (Throwable exception) {
            response = exception.getMessage();
            sendNotFound(httpExchange, response);
        }

        return response;

    }




}
