package ru.itis.tdportal;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    @Value("${ws.client.uri}")
    private String wsClientUri;

    private final WebClient.Builder clientBuilder;

    @GetMapping("/ws/user/{userId}/notification")
    public Flux<ServerSentEvent> handleNotification(@PathVariable Long userId) {
        return clientBuilder
                .build()
                .get()
                .uri(wsClientUri + "user/" + userId + "/notification")
                .retrieve()
                .bodyToFlux(ServerSentEvent.class);
    }
}
