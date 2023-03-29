package ru.itis.tdportal.mainservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.FluxSink;

@Getter
@Setter
@AllArgsConstructor
public class SubscriptionData {

    private Long userId;
    private FluxSink<ServerSentEvent> fluxSink;
}
