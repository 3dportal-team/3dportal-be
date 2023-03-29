package ru.itis.tdportal.mainservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import ru.itis.tdportal.mainservice.dtos.NotificationDto;
import ru.itis.tdportal.mainservice.models.SubscriptionData;
import ru.itis.tdportal.mainservice.models.entities.Notification;
import ru.itis.tdportal.mainservice.models.mappers.NotificationMapper;
import ru.itis.tdportal.mainservice.repositories.NotificationRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;
    private final NotificationMapper mapper;

    Map<Long, SubscriptionData> subscriptions = new ConcurrentHashMap<>();

    @Transactional(readOnly = true)
    public Flux<NotificationDto> asyncFindByUserId(Long userId) {
        List<NotificationDto> dtos = repository.findAllByUserId(userId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());

        return Flux.fromIterable(dtos);
    }

    public void subscribe(FluxSink<ServerSentEvent> sink, Long userId) {
        subscriptions.put(userId, new SubscriptionData(userId, sink));
    }

    @Transactional
    public void sendAndSave(NotificationDto dto) {
        Notification savedNotification = repository.save(mapper.toEntity(dto));

        ServerSentEvent<NotificationDto> event = ServerSentEvent
                .builder(mapper.toDto(savedNotification))
                .build();

        if (subscriptions.containsKey(dto.getUserId())) {
            subscriptions.get(dto.getUserId())
                    .getFluxSink()
                    .next(event);
        }
    }
}
