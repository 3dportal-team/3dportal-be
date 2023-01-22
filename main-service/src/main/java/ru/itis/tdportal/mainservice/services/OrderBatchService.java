package ru.itis.tdportal.mainservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.tdportal.mainservice.dtos.OrderBatchItemDto;
import ru.itis.tdportal.mainservice.models.entities.ModelFile;
import ru.itis.tdportal.mainservice.models.entities.OrderBatch;
import ru.itis.tdportal.mainservice.models.entities.OrderBatchItem;
import ru.itis.tdportal.mainservice.models.entities.OrderBatchItemID;
import ru.itis.tdportal.mainservice.models.enums.OrderBatchStatus;
import ru.itis.tdportal.mainservice.models.exceptions.FoundOrderBatchList;
import ru.itis.tdportal.mainservice.models.mappers.OrderBatchMapper;
import ru.itis.tdportal.mainservice.repositories.OrderBatchRepository;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderBatchService {

    //    @Value("${order.batch.ttl}")
    //    private Long orderBatchTTL;
    // TODO: удаление просроченной корзины

    private final AuthenticationService authService;
    private final OrderBatchRepository repository;
    private final ModelService modelService;
    private final OrderBatchMapper orderBatchMapper;


    @Transactional
    public void save(Long modelId) {
        ModelFile modelFile = modelService.getModelFileOrThrow(modelId);
        OrderBatch batch = getOrderBatchByStatusOrThrow(OrderBatchStatus.DRAFT);
        batch.setStatus(OrderBatchStatus.DRAFT);

        OrderBatchItem item = new OrderBatchItem();
        item.setId(new OrderBatchItemID(batch, modelFile));
        batch.getOrderBatchItems().add(item);

        repository.save(batch);
    }

    @Transactional(readOnly = true)
    protected OrderBatch getOrderBatchByStatusOrThrow(OrderBatchStatus status) {
        Long currentUserId = authService.getCurrentUser().getId();
        List<OrderBatch> batches = repository.findAllByStatusAndCreatorId(status, currentUserId);

        if (OrderBatchStatus.DRAFT.equals(status) && batches.size() > 1) {
            throw new FoundOrderBatchList(
                    String.format("Found order batch list for user with id = %s", currentUserId)
                    // TODO: обработать такую ситуацию или запретить ее на уровне бд
            );
        }

        return batches.stream()
                .findFirst()
                .orElseGet(() -> {
                    OrderBatch batch = new OrderBatch();
                    batch.setOrderBatchItems(Collections.emptyList());
                    return batch;
                });
    }

    public List<OrderBatchItemDto> getCurrentOrderBatchInStatusDraft() {
        List<OrderBatchItem> items = getOrderBatchByStatusOrThrow(OrderBatchStatus.DRAFT)
                .getOrderBatchItems();
        return orderBatchMapper.toListDto(items);
    }
}
