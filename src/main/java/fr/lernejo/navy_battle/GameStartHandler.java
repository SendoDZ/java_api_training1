package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class GameStartHandler implements HttpHandler {
    private final int serverPort;

    public GameStartHandler(int serverPort) {
        this.serverPort = serverPort;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!"POST".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(404, 0);
            exchange.getResponseBody().close();
            return;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        String jsonString = sb.toString();

        JSONObject requestJson;
        try {
            requestJson = new JSONObject(jsonString);

            if (!requestJson.has("id") || !requestJson.has("url") || !requestJson.has("message")) {
                sendResponse(exchange, 400, "Bad Request");
                return;
            }
        } catch (Exception e) {
            sendResponse(exchange, 400, "Bad Request");
            return;
        }

        JSONObject responseJson = new JSONObject();
        responseJson.put("id", "2aca7611-0ae4-49f3-bf63-75bef4769028");
        responseJson.put("url", "http://localhost:" + serverPort);
        responseJson.put("message", "May the best code win");

        sendResponse(exchange, 202, responseJson.toString());
    }

    private void sendResponse(HttpExchange exchange, int httpStatus, String response) throws IOException {
        exchange.sendResponseHeaders(httpStatus, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
