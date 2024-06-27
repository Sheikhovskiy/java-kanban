package handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import adapters.InstantAdapter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

import service.*;


public class BaseHttpHandler implements HttpHandler {

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

        if (pathSplitted[2] != null) {

            try {
                receivedId = Integer.parseInt(pathSplitted[2]);

            } catch (NumberFormatException exc) {
                httpExchange.sendResponseHeaders(400, 0);
                System.out.println(exc.getMessage());
            }
        }
        return receivedId;
    }











/*    class LocalDateAdapter extends TypeAdapter<LocalDateTime> {

        private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        @Override
        public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
            jsonWriter.value(localDateTime.format(format));
        }

        @Override
        public LocalDateTime read(JsonReader jsonReader) throws IOException {
            return LocalDateTime.parse(jsonReader.nextString(), format);
        }
    }*/




/*    protected static void writeResponse(HttpExchange httpExchange, String responseString, int responseCode) throws IOException {

        try (OutputStream os = httpExchange.getResponseBody()) {
            httpExchange.sendResponseHeaders(responseCode, 0);
            os.write(responseString.getBytes(StandardCharsets.UTF_8));
        }
        httpExchange.close();

    }*/


}
