package ru.itis.tdportal.paymentservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itis.tdportal.common.clients.constants.HttpHeader;
import ru.itis.tdportal.common.models.dtos.PaymentDto;
import ru.itis.tdportal.paymentservice.dtos.CreatedPaymentDto;
import ru.itis.tdportal.paymentservice.dtos.YookassaNotificationDto;
import ru.itis.tdportal.paymentservice.services.PaymentService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    // TODO: это должно быть internal
    public CreatedPaymentDto createPayment(
            @RequestHeader(name = HttpHeader.IdempotenceKey) UUID idempotenceKey,
            @RequestBody PaymentDto dto) {
        return paymentService.savePayment(dto, idempotenceKey);
    }

    @PutMapping("/{idempotenceKey}")
    public Map<String, String> movePaymentToStatusPending(
            @PathVariable UUID idempotenceKey) {
        return paymentService.movePaymentToStatusPending(idempotenceKey);
    }

    @PostMapping("/notify")
    // TODO: это должно быть external
    public void movePaymentToStatus(@RequestBody YookassaNotificationDto dto) {
        paymentService.movePaymentToStatusByEvent(dto); // TODO: может быть refund
    }
}
