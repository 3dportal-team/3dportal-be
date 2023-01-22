package ru.itis.tdportal.paymentservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.tdportal.paymentservice.dtos.CreatedPaymentDto;
import ru.itis.tdportal.paymentservice.dtos.PaymentDto;
import ru.itis.tdportal.paymentservice.models.enums.PaymentMethodData;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final YookassaService yookassaService;

    // TODO: при каждом запросе тут будет обращение в ЮКассу
    public List<PaymentMethodData> getSupportedPaymentMethods() {
        return yookassaService.getAccountInfo().getPaymentMethods();
    }

    public CreatedPaymentDto savePayment(PaymentDto dto) {
        UUID idempotenceKey = UUID.randomUUID();
        return yookassaService.createPayment(dto, idempotenceKey);
    }
}
