package ru.itis.tdportal.mainservice.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.itis.tdportal.common.models.mappers.MoneyMapper;
import ru.itis.tdportal.mainservice.dtos.OrderBatchItemDto;
import ru.itis.tdportal.mainservice.models.entities.OrderBatchItem;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {ModelFileMapper.class, MoneyMapper.class})
public interface OrderBatchItemMapper {

    @Mapping(target = "id.modelFile.id", source = "modelId")
    OrderBatchItem toEntity(OrderBatchItemDto dto);

    @Mapping(target = "modelId", source = "id.modelFile.id")
    OrderBatchItemDto toDto(OrderBatchItem entity);

}
