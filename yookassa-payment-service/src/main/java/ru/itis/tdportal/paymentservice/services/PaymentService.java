package ru.itis.tdportal.paymentservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.itis.tdportal.common.models.dtos.PaymentDto;
import ru.itis.tdportal.common.models.mappers.MoneyMapper;
import ru.itis.tdportal.paymentservice.dtos.CreatedPaymentDto;
import ru.itis.tdportal.paymentservice.models.entities.Payment;
import ru.itis.tdportal.paymentservice.models.enums.ConfirmationType;
import ru.itis.tdportal.paymentservice.models.exceptions.PaymentNotFoundException;
import ru.itis.tdportal.paymentservice.models.mappers.PaymentMapper;
import ru.itis.tdportal.paymentservice.repositories.PaymentRepository;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    @Value("${yookassa.return-url}")
    private String returnUrl;

    private final MoneyMapper moneyMapper;
    private final PaymentMapper paymentMapper;
    private final PaymentRepository repository;
    private final YookassaService yookassaService;

    @Transactional
    public CreatedPaymentDto savePayment(PaymentDto dto, UUID idempotenceKey) {
        Payment payment = paymentMapper.toEntity(dto);
        payment.setIdempotenceKey(idempotenceKey);
        return paymentMapper.toDto(repository.save(payment));
    }

    @Transactional
    public Payment getPaymentByKeyOrThrow(UUID idempotenceKey) {
        return repository.findByIdempotenceKey(idempotenceKey)
                .orElseThrow(() -> new PaymentNotFoundException(
                        String.format("Payment not found by key %s", idempotenceKey
                        ))
                );
    }

    @Transactional
    public Map<String, String> movePaymentToStatusPending(UUID idempotenceKey) {
        Payment payment = getPaymentByKeyOrThrow(idempotenceKey);
        CreatedPaymentDto dto = paymentMapper.toDto(payment);

        // TODO: убрать хардкод
        dto.setConfirmation(Map.of(
                "return_url", returnUrl,
                "type", ConfirmationType.REDIRECT.getValue()
                )
        );

        CreatedPaymentDto pendingPayment = yookassaService.createPayment(dto, idempotenceKey);

        paymentMapper.merge(payment, pendingPayment);

        return pendingPayment.getConfirmation();
    }
}
