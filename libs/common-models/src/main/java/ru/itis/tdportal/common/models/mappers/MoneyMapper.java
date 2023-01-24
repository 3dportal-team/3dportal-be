package ru.itis.tdportal.common.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.itis.tdportal.common.models.dtos.MoneyDto;
import ru.itis.tdportal.common.models.entities.Money;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MoneyMapper {

    MoneyDto toDto(Money source);

    Money toEntity(MoneyDto source);
}
