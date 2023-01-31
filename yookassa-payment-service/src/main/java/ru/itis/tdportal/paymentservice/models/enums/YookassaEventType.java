package ru.itis.tdportal.paymentservice.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.itis.tdportal.common.models.enums.PaymentStatus;

@Getter
@AllArgsConstructor
public enum YookassaEventType {

    PAYMENT_WAITING_FOR_CAPTURE("payment.waiting_for_capture", PaymentStatus.WAITING_FOR_CAPTURE),
    PAYMENT_SUCCEEDED("payment.succeeded", PaymentStatus.SUCCEEDED),
    PAYMENT_CANCELED("payment.canceled", PaymentStatus.CANCELED);
    // TODO: "refund.succeeded"

    @JsonValue
    private String value;
    private PaymentStatus objectStatus;

}
