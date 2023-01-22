package ru.itis.tdportal.mainservice.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.itis.tdportal.mainservice.dtos.OrderBatchItemDto;
import ru.itis.tdportal.mainservice.models.entities.OrderBatchItem;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = OrderBatchItemMapper.class)
public interface OrderBatchMapper {

    List<OrderBatchItemDto> toListDto(List<OrderBatchItem> entities);

    @Mapping(target = "modelId", source = "id.modelFile.id")
    OrderBatchItemDto toDto(OrderBatchItem entity);
}
