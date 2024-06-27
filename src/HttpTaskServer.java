import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Instant;

import adapters.InstantAdapter;
import com.google.gson.*;

import com.sun.net.httpserver.HttpServer;
import handlers.*;
import service.InMemoryTaskManager;
import service.TaskManager;

//import handlers.TaskHandler;

public class HttpTaskServer {

    private static final int PORT = 8080;
    private final HttpServer httpServer;

    public static void main(String[] args) throws IOException {

/*        try {
            TaskManager manager = new InMemoryTaskManager();
            HttpTaskServer server = new HttpTaskServer(manager, PORT);
            //server.start();

        } catch (IOException exception) {
            exception.printStackTrace();
        }*/

    }

    public HttpTaskServer(TaskManager manager, int port) throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        httpServer.createContext("/tasks", new TaskHandler(manager));

        httpServer.createContext("/subtasks", new SubtaskHandler(manager));
        httpServer.createContext("/epics", new EpicHandler(manager));
        httpServer.createContext("/history", new HistoryHandler(manager));
        httpServer.createContext("/prioritized", new PrioritizedHandler(manager));
    }



    static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Instant.class, new InstantAdapter());
        return gsonBuilder.create();

    }


    public void start() {
        System.out.println("Запуск сервера " + PORT);
        httpServer.start();
        System.out.println("Сервер запущен");
    }

    public void stop() {
        System.out.println("Остановка сервера " + PORT);
        httpServer.stop(0);
        System.out.println("Сервер остановлен");
    }
}





