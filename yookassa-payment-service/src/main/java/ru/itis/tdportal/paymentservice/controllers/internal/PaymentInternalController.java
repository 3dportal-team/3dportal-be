package ru.itis.tdportal.paymentservice.controllers.internal;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itis.tdportal.common.clients.constants.HttpHeader;
import ru.itis.tdportal.common.models.dtos.PaymentDto;
import ru.itis.tdportal.paymentservice.dtos.CreatedPaymentDto;
import ru.itis.tdportal.paymentservice.services.PaymentService;

import java.util.UUID;

@Hidden
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/api/payment")
public class PaymentInternalController {

    private final PaymentService paymentService;

    @PostMapping
    // TODO: это должно быть internal
    public CreatedPaymentDto createPayment(
            @RequestHeader(name = HttpHeader.IdempotenceKey) UUID idempotenceKey,
            @RequestBody PaymentDto dto) {
        return paymentService.savePayment(dto, idempotenceKey);
    }
}
