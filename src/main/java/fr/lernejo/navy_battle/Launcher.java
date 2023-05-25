package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Launcher {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: Launcher [port]");
            System.exit(-1);
        }
        int port = Integer.parseInt(args[0]);
        startServer(port);
    }

    private static void startServer(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.setExecutor(Executors.newFixedThreadPool(1));
        createContexts(server, port);
        server.start();
    }

    private static void createContexts(HttpServer server, int port) {
        server.createContext("/ping", new PingHandler());
        server.createContext("/api/game/start", new GameStartHandler(port));
    }
}

