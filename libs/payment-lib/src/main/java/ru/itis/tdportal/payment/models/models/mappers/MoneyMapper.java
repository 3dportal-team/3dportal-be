package ru.itis.tdportal.payment.models.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.itis.tdportal.payment.models.dtos.MoneyDto;
import ru.itis.tdportal.payment.models.models.Money;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MoneyMapper {

    MoneyDto toDto(Money source);

    Money toEntity(MoneyDto source);
}
