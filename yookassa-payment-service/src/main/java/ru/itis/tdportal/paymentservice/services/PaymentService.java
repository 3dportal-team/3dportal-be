package ru.itis.tdportal.paymentservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.tdportal.common.clients.feign.MainServiceClient;
import ru.itis.tdportal.common.models.dtos.MoneyDto;
import ru.itis.tdportal.common.models.dtos.PaymentDto;
import ru.itis.tdportal.common.models.entities.Money;
import ru.itis.tdportal.common.models.enums.PaymentStatus;
import ru.itis.tdportal.paymentservice.dtos.CreatedPaymentDto;
import ru.itis.tdportal.paymentservice.dtos.PayoutDto;
import ru.itis.tdportal.paymentservice.dtos.YookassaNotificationDto;
import ru.itis.tdportal.paymentservice.models.entities.Payment;
import ru.itis.tdportal.paymentservice.models.enums.ConfirmationType;
import ru.itis.tdportal.paymentservice.models.enums.PayoutStatus;
import ru.itis.tdportal.paymentservice.models.exceptions.PaymentNotFoundException;
import ru.itis.tdportal.paymentservice.models.mappers.PaymentMapper;
import ru.itis.tdportal.paymentservice.repositories.PaymentRepository;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    @Value("${yookassa.return-url}")
    private String returnUrl;

    private final MainServiceClient mainServiceClient;
    private final PaymentMapper paymentMapper;
    private final PaymentRepository repository;
    private final YookassaService yookassaService;
    private final PayoutService payoutService;

    @Transactional
    public CreatedPaymentDto savePayment(PaymentDto dto, UUID idempotenceKey) {
        Payment payment = paymentMapper.toEntity(dto);
        payment.setIdempotenceKey(idempotenceKey);
        return paymentMapper.toDto(repository.save(payment));
    }

    @Transactional(readOnly = true)
    public Payment getPaymentByKeyOrThrow(UUID idempotenceKey) {
        return repository.findByIdempotenceKey(idempotenceKey)
                .orElseThrow(() -> new PaymentNotFoundException(
                        String.format("Payment not found by key %s", idempotenceKey
                        ))
                );
    }

    @Transactional(readOnly = true)
    public Payment getByYooIdOrThrow(UUID yooId) {
        return repository.findByYooId(yooId)
                .orElseThrow(() -> new PaymentNotFoundException(
                        String.format("Payment not found by yookassa ID %s", yooId
                        ))
                );
    }

    @Transactional
    public Map<String, String> movePaymentToStatusPending(UUID idempotenceKey) {
        Payment payment = getPaymentByKeyOrThrow(idempotenceKey);
        CreatedPaymentDto dto = paymentMapper.toDto(payment);
        dto.setCapture(true);

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

    @Transactional
    public void movePaymentToStatusByEvent(YookassaNotificationDto dto) {
        // TODO: проверка на NPE
        Payment payment = getByYooIdOrThrow(dto.getObject().getId());
        PaymentStatus status = dto.getEvent().getObjectStatus();
        payment.setStatus(status);

        mainServiceClient.updatePaymentStatusNotification(payment.getIdempotenceKey(), status);

        if (PaymentStatus.SUCCEEDED.equals(status)) {
            PayoutDto payoutDto = new PayoutDto();
            payoutDto.setPayoutToken(payment.getReceiver().getPayoutToken());

            Money paymentAmount = payment.getAmount();
            // TODO: должна быть нормальная формула для расчета выплаты по заказу
            payoutDto.setAmount(new MoneyDto(paymentAmount.getValue(), paymentAmount.getCurrency()) );

            payoutDto.setDescription(String.format("Выплата по заказу %s", payment.getIdempotenceKey()));

            payoutService.createPayout(new PayoutDto(), payment.getIdempotenceKey());
        }

        log.info(String.format(
                "Payment %s successfully moved to status %s",
                payment.getId(), status)
        );
    }
}
