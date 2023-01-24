package ru.itis.tdportal.mainservice.dtos;

import lombok.Data;
import ru.itis.tdportal.common.models.dtos.MoneyDto;

@Data
public class OrderBatchItemDto {

    private Long modelId;

    private MoneyDto price;

}
