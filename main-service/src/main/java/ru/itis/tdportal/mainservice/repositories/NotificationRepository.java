package ru.itis.tdportal.mainservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.tdportal.mainservice.models.entities.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByUserId(Long userId);
}
