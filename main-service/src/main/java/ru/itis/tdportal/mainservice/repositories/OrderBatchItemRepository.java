package ru.itis.tdportal.mainservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.tdportal.mainservice.models.entities.OrderBatchItem;
import ru.itis.tdportal.mainservice.models.entities.OrderBatchItemID;

@Repository
public interface OrderBatchItemRepository extends JpaRepository<OrderBatchItem, OrderBatchItemID> {


//    Optional<OrderBatchItem> findOrderBatchItemByModelFile_IdAndOrderBatch_Id(Long modelId, Long batchId);
}
