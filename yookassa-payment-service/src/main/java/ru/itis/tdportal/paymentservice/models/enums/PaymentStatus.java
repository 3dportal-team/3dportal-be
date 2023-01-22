package ru.itis.tdportal.paymentservice.models.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PaymentStatus {

    PENDING("pending"),
    WAITING_FOR_CAPTURE("waiting_for_capture"),
    SUCCEEDED("succeeded"),
    CANCELED("canceled");

    private String value;
}
