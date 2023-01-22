package ru.itis.tdportal.mainservice.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.itis.tdportal.mainservice.dtos.OrderBatchItemDto;
import ru.itis.tdportal.mainservice.models.entities.OrderBatchItem;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = ModelFileMapper.class)
public interface OrderBatchItemMapper {

    OrderBatchItem toEntity(OrderBatchItemDto dto);


}
