package ru.itis.tdportal.mainservice.dtos;

import lombok.Data;
import ru.itis.tdportal.mainservice.models.enums.OrderBatchStatus;
import ru.itis.tdportal.payment.models.dtos.MoneyDto;

import java.util.Set;
import java.util.UUID;

@Data
public class OrderBatchDto {

    private Long id;

    private Set<OrderBatchItemDto> orderBatchItems;

    private OrderBatchStatus status;

    private MoneyDto price;

    private UUID uuid;
}
