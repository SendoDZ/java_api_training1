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
    private final BattleGame game;

    public GameStartHandler(BattleGame game) {
        this.game = game;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!"POST".equals(exchange.getRequestMethod())) {
            sendResponse(exchange, 404, "Not Found");
            return;
        }

        JSONObject requestJson = getRequestBody(exchange);

        if (requestJson == null) {
            sendResponse(exchange, 400, "Bad Request");
            return;
        }

        JSONObject responseJson = generateResponseJson();
        sendResponse(exchange, 202, responseJson.toString());
    }

    private JSONObject getRequestBody(HttpExchange exchange) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return new JSONObject(sb.toString());
        } catch (Exception e) {
            return null;
        }
    }

    private JSONObject generateResponseJson() {
        JSONObject responseJson = new JSONObject();
        responseJson.put("id", "2aca7611-0ae4-49f3-bf63-75bef4769028");
        responseJson.put("url", "http://localhost:" + serverPort);
        responseJson.put("message", "May the best code win");
        return responseJson;
    }

    private void sendResponse(HttpExchange exchange, int httpStatus, String response) throws IOException {
        exchange.sendResponseHeaders(httpStatus, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
