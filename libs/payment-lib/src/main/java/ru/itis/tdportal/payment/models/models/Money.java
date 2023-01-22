package ru.itis.tdportal.payment.models.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.itis.tdportal.payment.models.enums.Currency;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@Embeddable
public class Money {

    @Column(name = "value")
    private Double value;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;
}
