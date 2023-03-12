package ru.itis.tdportal.mainservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.tdportal.mainservice.models.entities.OrderBatch;
import ru.itis.tdportal.mainservice.models.enums.OrderBatchStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderBatchRepository extends JpaRepository<OrderBatch, Long> {

    List<OrderBatch> findAllByStatusAndCreatorId(OrderBatchStatus status, Long creatorId);

    Optional<OrderBatch> findByUuid(UUID uuid);

    List<OrderBatch> findAllByStatusInAn();
}
