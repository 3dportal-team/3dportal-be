package ru.itis.tdportal.paymentservice.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethodData {

    BANK_CARD("bank_card"),
    YOO_MONEY("yoo_money");

    @JsonValue
    private String value;

}
