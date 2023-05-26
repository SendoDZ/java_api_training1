package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class FireHandler implements HttpHandler {
    private final String[] CONSEQUENCES = {"miss", "hit", "sunk"};

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String cell = getCellFromRequest(exchange.getRequestURI().getQuery());
        String consequence = getConsequence(cell);
        boolean shipLeft = checkIfShipLeft();

        JSONObject responseJson = new JSONObject();
        responseJson.put("consequence", consequence);
        responseJson.put("shipLeft", shipLeft);

        String response = responseJson.toString();
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String getCellFromRequest(String query) {
        String[] parts = query.split("=");
        return parts[1];
    }

    private String getConsequence(String cell) {
        Random random = new Random();
        return this.CONSEQUENCES[random.nextInt(this.CONSEQUENCES.length)];
    }

    private boolean checkIfShipLeft() {
        return true;
    }
}