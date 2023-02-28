package ru.itis.tdportal.paymentservice.controllers.openapi;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itis.tdportal.paymentservice.services.PaymentService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PutMapping("/{idempotenceKey}")
    public Map<String, String> movePaymentToStatusPending(
            @PathVariable UUID idempotenceKey) {
        return paymentService.movePaymentToStatusPending(idempotenceKey);
    }
}
