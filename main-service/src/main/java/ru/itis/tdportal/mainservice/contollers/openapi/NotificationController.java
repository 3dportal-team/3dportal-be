package ru.itis.tdportal.mainservice.contollers.openapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.itis.tdportal.mainservice.dtos.NotificationDto;
import ru.itis.tdportal.mainservice.services.NotificationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @GetMapping(path = "/ws/user/{userId}/notification", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent> getKey(@PathVariable Long userId) {
        return Flux.create(fluxSink -> {
                    List<NotificationDto> notificationDtos = service.findByUserId(userId);
                    service.subscribe(fluxSink, userId);
                    fluxSink.next(ServerSentEvent.builder()
                            .data(notificationDtos)
                            .build());
                }
        );
    }
}
