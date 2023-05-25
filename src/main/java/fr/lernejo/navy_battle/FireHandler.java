package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class FireHandler implements HttpHandler {
    private final BattleGame game;

    public FireHandler(BattleGame game) {
        this.game = game;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("GET")) {
            String cell = exchange.getRequestURI().getQuery().substring(5);  // Obtenez la cellule cible de la requête
            Map<String, Object> result = game.fire(cell);
            String responseBody = new JSONObject(result).toString();
            exchange.sendResponseHeaders(200, responseBody.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(responseBody.getBytes());
            os.close();
        } else {
            exchange.sendResponseHeaders(405, -1);  // Méthode non autorisée
        }
    }
}
