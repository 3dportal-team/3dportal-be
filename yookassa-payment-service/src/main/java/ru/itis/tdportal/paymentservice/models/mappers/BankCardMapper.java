package ru.itis.tdportal.paymentservice.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.itis.tdportal.paymentservice.dtos.BankCardDto;
import ru.itis.tdportal.paymentservice.models.entities.BankCard;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BankCardMapper {

    void merge(@MappingTarget BankCard bankCard, BankCardDto bankCardDto);

    BankCardDto toDto(BankCard bankCard);
}
