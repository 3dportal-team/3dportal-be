package ru.itis.tdportal.mainservice.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import ru.itis.tdportal.mainservice.models.enums.NotificationType;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Планируется перевод функционала уведомлений на отдельный микросервис,
     * потому не PortalUser
     */
    @Column(name = "receiver_id")
    private Long receiverId;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private String text;

    @CreatedDate
    @Column(name = "created_at")
    protected Instant createdAt;
}
