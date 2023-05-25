package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Launcher {
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/ping", new PingHandler());
        server.createContext("/api/game/start", new GameStartHandler());

        ExecutorService executor = Executors.newFixedThreadPool(1);
        server.setExecutor(executor);

        server.start();
    }
}
