package ru.itis.tdportal.mainservice.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ru.itis.tdportal.mainservice.models.enums.NotificationType;

import java.time.Instant;

@Data
public class NotificationDto {

    @JsonIgnore
    private Long id;

    @JsonIgnore
    private Long userId;

    private NotificationType type;

    private Instant createdAt;

    private String text;
}
