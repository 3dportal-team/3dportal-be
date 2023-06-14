package ru.itis.tdportal.paymentservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import ru.itis.tdportal.paymentservice.dtos.EventObjectDto;
import ru.itis.tdportal.paymentservice.dtos.PayoutDto;
import ru.itis.tdportal.paymentservice.dtos.YookassaNotificationDto;
import ru.itis.tdportal.paymentservice.models.entities.BankCard;
import ru.itis.tdportal.paymentservice.models.entities.Payment;
import ru.itis.tdportal.paymentservice.models.enums.ConfirmationType;
import ru.itis.tdportal.paymentservice.models.exceptions.PaymentNotFoundException;
import ru.itis.tdportal.paymentservice.models.mappers.PaymentMapper;
import ru.itis.tdportal.paymentservice.repositories.PaymentRepository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
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

    private final BankCardService bankCardService;
    private final PayoutService payoutService;

    @Transactional
    public CreatedPaymentDto savePayment(PaymentDto dto, UUID idempotenceKey) {
        Payment payment = paymentMapper.toEntity(dto);

        BankCard bankCard = bankCardService.getBankCardOrThrow(dto.getReceiverId());
        payment.setBankCard(bankCard);
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
        EventObjectDto object = dto.getObject();
        if (Objects.isNull(object) || Objects.isNull(object.getId())) {
            log.error(
                    String.format("Received invalid event %s", object)
            );
            return;
        }

        PaymentStatus status = dto.getEvent().getObjectStatus();
        log.info(String.format("Payment moving %s to status %s started", object.getId(), status));
        try {
            Payment payment = getByYooIdOrThrow(dto.getObject().getId());
            payment.setStatus(status);

            mainServiceClient.updatePaymentStatusNotification(payment.getIdempotenceKey(), status);

            if (PaymentStatus.SUCCEEDED.equals(status)) {
                PayoutDto payoutDto = new PayoutDto();
                payoutDto.setPayoutToken(payment.getBankCard().getPayoutToken());

                Money paymentAmount = payment.getAmount();
                payoutDto.setAmount(new MoneyDto(new BigDecimal(paymentAmount.getValue()).setScale(2), paymentAmount.getCurrency()));
                payoutDto.setDescription(String.format("Выплата по заказу %s", payment.getIdempotenceKey()));

                payoutService.createPayout(payoutDto, payment.getIdempotenceKey());
            }

            log.info(
                    String.format("Payment %s successfully moved to status %s", payment.getId(), status)
            );
        } catch (Exception e) {
            log.error(String.format(
                    "Payment moving with YOOID %s to status %s failed causing: %s",
                    dto.getObject().getId(),
                    dto.getEvent().getObjectStatus(),
                    e.getMessage()
            ));
        }
    }
}
