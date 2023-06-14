package ru.itis.tdportal.paymentservice.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.itis.tdportal.common.models.dtos.PaymentDto;
import ru.itis.tdportal.common.models.mappers.MoneyMapper;
import ru.itis.tdportal.paymentservice.dtos.CreatedPaymentDto;
import ru.itis.tdportal.paymentservice.models.entities.Payment;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {MoneyMapper.class})
public interface PaymentMapper {

    Payment toEntity(PaymentDto source);

    CreatedPaymentDto toDto(Payment save);

    void merge(@MappingTarget Payment payment, CreatedPaymentDto pendingPayment);
}
