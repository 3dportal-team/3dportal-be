package ru.itis.tdportal.common.models.entities;

import lombok.Getter;
import lombok.Setter;
import ru.itis.tdportal.common.models.enums.Currency;

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
