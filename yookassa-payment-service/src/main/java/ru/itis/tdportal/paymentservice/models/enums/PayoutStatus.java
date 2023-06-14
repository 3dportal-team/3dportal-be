package ru.itis.tdportal.paymentservice.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayoutStatus {

    CREATED("created"),
    SUCCEEDED("succeeded"),
    CANCELED("canceled");

    private String value;
}
