package handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import adapters.InstantAdapter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

import service.*;


public abstract class BaseHttpHandler implements HttpHandler {

    TaskManager taskManager = Managers.getDefaultTaskManager();
    HistoryManager historyManager = Managers.getDefaultHistory();
    FileBackedTaskManager fileBackedTaskManager = Managers.getDefaultFileBackedManager();

    public BaseHttpHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    // sendText
    protected void sendText(HttpExchange httpExchange, String text) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        httpExchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        httpExchange.sendResponseHeaders(200, resp.length);
        httpExchange.getResponseBody().write(resp);
        httpExchange.close();

    }

    // sendNotFound
    protected void sendNotFound(HttpExchange httpExchange, String text) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        httpExchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        httpExchange.sendResponseHeaders(404, resp.length);
        httpExchange.getResponseBody().write(resp);
        httpExchange.close();
    }

    protected void sendInteractions(HttpExchange httpExchange, String text) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        httpExchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        httpExchange.sendResponseHeaders(409, resp.length);
        httpExchange.getResponseBody().write(resp);
        httpExchange.close();
    }


    public void getClientMethod(HttpExchange httpExchange) throws IOException {

        String response;

        String method = httpExchange.getRequestMethod();
        METHOD_TYPE methodType = METHOD_TYPE.valueOf(method);


        switch (methodType) {
            case METHOD_TYPE.GET:
                response = handleGetRequest(httpExchange);
                break;
            case METHOD_TYPE.POST:
                response = handlePostRequest(httpExchange);
                break;
            case METHOD_TYPE.DELETE:
                response = handleDeleteRequest(httpExchange);
                break;
            default:
                response = "METHOD_NOT_ALLOWED";
                sendResponse(httpExchange, 405, response);
        }

    }

    protected void sendResponse(HttpExchange httpExchange, int statusCode, String response) throws IOException {

        byte[] responseInBytes = response.getBytes();

        httpExchange.sendResponseHeaders(statusCode, responseInBytes.length);

        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(responseInBytes);
        }
    }

    protected  String handleDeleteRequest(HttpExchange httpExchange) throws IOException {
        String response = "METHOD_NOT_ALLOWED";
        sendResponse(httpExchange, 405, response);
        return response;
    }

    protected  String handlePostRequest(HttpExchange httpExchange) throws IOException {
        String response = "METHOD_NOT_ALLOWED";
        sendResponse(httpExchange, 405, response);
        return response;
    }

    protected  String handleGetRequest(HttpExchange httpExchange) throws IOException {
        String response = "METHOD_NOT_ALLOWED";
        sendResponse(httpExchange, 405, response);
        return response;
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }


    protected static String toGson(Object obj) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantAdapter())
                .create();
        return gson.toJson(obj);
    }


    protected static <T> T fromGson(String json, Class<T> clazz) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantAdapter())
                .create();
        return gson.fromJson(json, clazz);
    }


    public static int checkIfTaskHasId(HttpExchange httpExchange) throws IOException {

        int receivedId = -1;

        String path = httpExchange.getRequestURI().getPath();
        String[] pathSplitted = path.split("/");

        try {
            if (pathSplitted.length > 2 && pathSplitted[2] != null) {

                try {
                    receivedId = Integer.parseInt(pathSplitted[2]);

                } catch (NumberFormatException exc) {
                    httpExchange.sendResponseHeaders(400, 0);
                    System.out.println(exc.getMessage());
                }
            }
        } catch (Throwable exception) {
            System.out.println(exception.getMessage());
        }
        return receivedId;
    }

    public enum METHOD_TYPE  {
        GET,
        POST,
        DELETE;

    }

}
