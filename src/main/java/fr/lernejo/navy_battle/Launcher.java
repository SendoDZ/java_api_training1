package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Launcher {
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        BattleGame game = new BattleGame();
        HttpServer server = createServer(port);
        configureContexts(server, game);
        startServer(server);
        startClientIfRequested(args, port);
    }

    private static HttpServer createServer(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        ExecutorService executor = Executors.newFixedThreadPool(1);
        server.setExecutor(executor);
        return server;
    }

    private static void configureContexts(HttpServer server, BattleGame game) {
        server.createContext("/ping", new PingHandler());
        server.createContext("/api/game/start", new GameStartHandler(game));
        server.createContext("/api/game/fire", new FireHandler(game));
    }

    private static void startServer(HttpServer server) {
        server.start();
    }

    private static void startClientIfRequested(String[] args, int port) {
        if (args.length > 1) {
            String adversaryUrl = args[1];
            BattleGameClient battleGameClient = new BattleGameClient();
            try {
                battleGameClient.sendStartGameRequest(adversaryUrl, port);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
