package ru.itis.tdportal.paymentservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.tdportal.paymentservice.dtos.PayoutDto;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PayoutService {

    private final YookassaService yookassaService;

    public void createPayout(PayoutDto dto, UUID idempotenceKey) {
        yookassaService.createPayout(dto, idempotenceKey);
    }
}
