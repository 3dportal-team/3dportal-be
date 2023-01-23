package ru.itis.tdportal.mainservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.tdportal.mainservice.dtos.CartDto;
import ru.itis.tdportal.mainservice.dtos.OrderBatchDto;
import ru.itis.tdportal.mainservice.dtos.OrderBatchItemDto;
import ru.itis.tdportal.mainservice.models.entities.OrderBatch;
import ru.itis.tdportal.mainservice.models.enums.OrderBatchStatus;
import ru.itis.tdportal.mainservice.models.mappers.OrderBatchMapper;
import ru.itis.tdportal.mainservice.repositories.OrderBatchRepository;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderBatchService {

    private final CartService cartService;
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
        return orderBatchMapper.toDto(createdOrderBatch);
    }
}
