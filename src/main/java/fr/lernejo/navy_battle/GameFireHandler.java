package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameFireHandler implements HttpHandler {
    private static final Pattern FIRE_PATTERN = Pattern.compile("/api/game/fire\\?cell=([A-J][1-9])");

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        if (!requestMethod.equalsIgnoreCase("GET")) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, -1);
            return;
        }

        String requestURI = exchange.getRequestURI().toString();
        Matcher matcher = FIRE_PATTERN.matcher(requestURI);
        if (!matcher.matches()) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
            return;
        }

        String cell = matcher.group(1);
        CellStatus status = Game.getInstance().shoot(cell);

        String response = String.format("{\"consequence\": \"%s\", \"shipLeft\": %s}", status, Game.getInstance().hasShipsLeft());
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.getBytes().length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}