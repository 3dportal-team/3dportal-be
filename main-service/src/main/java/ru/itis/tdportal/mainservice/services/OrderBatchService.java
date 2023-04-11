package ru.itis.tdportal.mainservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.tdportal.common.clients.feign.YooPaymentServiceClient;
import ru.itis.tdportal.common.models.dtos.PaymentDto;
import ru.itis.tdportal.common.models.enums.PaymentStatus;
import ru.itis.tdportal.mainservice.dtos.CartDto;
import ru.itis.tdportal.mainservice.dtos.NotificationDto;
import ru.itis.tdportal.mainservice.dtos.OrderBatchDto;
import ru.itis.tdportal.mainservice.dtos.OrderBatchItemDto;
import ru.itis.tdportal.mainservice.models.entities.ModelFile;
import ru.itis.tdportal.mainservice.models.entities.OrderBatch;
import ru.itis.tdportal.mainservice.models.enums.ModelToUserRelation;
import ru.itis.tdportal.mainservice.models.enums.NotificationType;
import ru.itis.tdportal.mainservice.models.enums.OrderBatchStatus;
import ru.itis.tdportal.mainservice.models.exceptions.OrderBatchNotFoundException;
import ru.itis.tdportal.mainservice.models.mappers.OrderBatchMapper;
import ru.itis.tdportal.mainservice.repositories.OrderBatchRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderBatchService {

    private final YooPaymentServiceClient client;

    private final CartService cartService;
    private final ModelFileAccessService accessService;
    private final NotificationService notificationService;

    private final OrderBatchRepository repository;
    private final OrderBatchMapper orderBatchMapper;

    @Transactional
    public OrderBatchDto createOrderBatchDraft() {
        CartDto cartDto = cartService.getCurrentUserCart();

        Set<OrderBatchItemDto> orderBatchItemDtos = cartDto.getModelFiles().stream()
                .filter(model -> Objects.nonNull(model.getPrice()) && Objects.nonNull(model.getPrice().getValue()))
                .map(model -> {
                    OrderBatchItemDto item = new OrderBatchItemDto();
                    item.setModelId(model.getId());
                    item.setPrice(model.getPrice());
                    return item;
                })
                .collect(Collectors.toSet());

        OrderBatchDto dto = new OrderBatchDto();
        dto.setOrderBatchItems(orderBatchItemDtos);
        dto.setUuid(UUID.randomUUID());

        OrderBatch orderBatch = orderBatchMapper.toEntity(dto);
        orderBatch.setStatus(OrderBatchStatus.DRAFT);

        OrderBatch createdOrderBatch = repository.save(orderBatch);
        cartService.clearCurrentUserCart();

        OrderBatchDto orderBatchDto = orderBatchMapper.toDto(createdOrderBatch);
        PaymentDto paymentDto = new PaymentDto(
                PaymentStatus.CREATED,
                String.format("Платеж по заказу %s", dto.getUuid()),
                orderBatchDto.getPrice(),
                false
        );
        client.createPayment(createdOrderBatch.getUuid(), paymentDto);

        return orderBatchDto;
    }

    @Transactional
    public void updateOrderBatchStatus(UUID uuid, PaymentStatus status) {
        OrderBatch order = repository.findByUuid(uuid)
                .orElseThrow(() -> new OrderBatchNotFoundException(String.format(
                        "Order was not found by uuid %s", uuid
                )));

        switch (status) {
            case SUCCEEDED:
                order.setStatus(OrderBatchStatus.PAID);
                order.getOrderBatchItems().forEach(orderItem -> {
                    ModelFile modelFile = orderItem.getId().getModelFile();
                    accessService.saveAccess(modelFile, order.getCreatorId(), ModelToUserRelation.AVAILABLE_FOR_DOWNLOADING);

                    NotificationDto dto = new NotificationDto(); // TODO: вынести
                    dto.setReceiverId(modelFile.getOwner().getId());
                    dto.setType(NotificationType.TO_SELLER_MODEL_PURCHASED);
                    dto.setText(String.format(
                            NotificationType.TO_SELLER_MODEL_PURCHASED.getMessage(),
                            List.of(modelFile.getId()).toArray())
                    );

                    notificationService.sendAndSave(dto);
                });
                break;
            case CANCELED:
                order.setStatus(OrderBatchStatus.ERROR);
                break;
        }
    }
}
