package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import model.Task;

import service.TaskManager;

import java.io.IOException;

import java.util.List;

public class HistoryHandler extends BaseHttpHandler {

    private final Gson gson;

    public HistoryHandler(TaskManager taskManager) {
        super(taskManager);
        this.gson = new Gson();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("Началась обработка /tasks запроса от клиента");

        getClientMethod(httpExchange);

    }


    @Override
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

}
