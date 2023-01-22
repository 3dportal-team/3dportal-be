package ru.itis.tdportal.paymentservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itis.tdportal.paymentservice.dtos.CreatedPaymentDto;
import ru.itis.tdportal.paymentservice.dtos.PaymentDto;
import ru.itis.tdportal.paymentservice.models.enums.PaymentMethodData;
import ru.itis.tdportal.paymentservice.services.PaymentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/info")
    public List<PaymentMethodData> getSupportedPaymentMethods() {
        return paymentService.getSupportedPaymentMethods();
    }

    @PostMapping
    public CreatedPaymentDto createPayment(@RequestBody PaymentDto dto) {
        return paymentService.savePayment(dto);
    }
}
