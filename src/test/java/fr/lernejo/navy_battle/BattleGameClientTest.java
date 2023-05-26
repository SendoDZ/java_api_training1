package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BattleGameClientTest {
    @Test
    void sendStartGameRequestTest() throws Exception {
        // Mock dependencies
        HttpClient client = Mockito.mock(HttpClient.class);
        HttpResponse<String> response = Mockito.mock(HttpResponse.class);

        // Define behavior
        when(response.body()).thenReturn("Response body");
        when(client.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(response);

        // Create instance with mock dependencies
        BattleGameClient battleGameClient = new BattleGameClient();

        // Inject mock dependencies
        java.lang.reflect.Field clientField = BattleGameClient.class.getDeclaredField("client");
        clientField.setAccessible(true);
        clientField.set(battleGameClient, client);

        // Call method to test
        battleGameClient.sendStartGameRequest("http://adversaryUrl", 8080);

        // Verification
        Mockito.verify(client).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }
}
