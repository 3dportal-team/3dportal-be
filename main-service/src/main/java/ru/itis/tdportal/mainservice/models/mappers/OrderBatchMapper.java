package ru.itis.tdportal.mainservice.models.mappers;

import org.mapstruct.*;
import ru.itis.tdportal.common.models.dtos.MoneyDto;
import ru.itis.tdportal.common.models.enums.Currency;
import ru.itis.tdportal.common.models.mappers.MoneyMapper;
import ru.itis.tdportal.mainservice.dtos.OrderBatchDto;
import ru.itis.tdportal.mainservice.models.entities.OrderBatch;
import ru.itis.tdportal.mainservice.models.entities.OrderBatchItemID;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {OrderBatchItemMapper.class, MoneyMapper.class})
public interface OrderBatchMapper {

    OrderBatch toEntity(OrderBatchDto source);

    OrderBatchDto toDto(OrderBatch source);

    @BeforeMapping
    default void setPrice(OrderBatchDto source) {
        Double sum = source.getOrderBatchItems().stream()
                .mapToDouble(item -> item.getPrice().getValue().doubleValue())
                .sum();
        source.setPrice(new MoneyDto(new BigDecimal(sum), Currency.RUB));
    }

    @AfterMapping
    default void setOrderItems(@MappingTarget OrderBatch target) {
        target.getOrderBatchItems()
                .forEach(item -> item.setId(new OrderBatchItemID(target, item.getId().getModelFile())));
    }

}
