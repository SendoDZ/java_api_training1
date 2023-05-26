package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class FireHandlerTest {
    @Test
    void handleTest() throws Exception {
        // Mock dependencies
        HttpClient client = Mockito.mock(HttpClient.class);
        HttpResponse<String> response = Mockito.mock(HttpResponse.class);

        // Define behavior
        when(response.body()).thenReturn("Response body");
        when(client.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(response);

        // Create HttpRequest
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/api/game/fire?cell=B5"))
                .build();

        // Create instance
        FireHandler handler = new FireHandler();

        // Verification
        Mockito.verify(client).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }
}
