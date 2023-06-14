package ru.itis.tdportal.paymentservice.models.entities;

import lombok.Getter;
import lombok.Setter;
import ru.itis.tdportal.common.models.entities.Money;
import ru.itis.tdportal.paymentservice.models.enums.PayoutStatus;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "payout")
public class Payout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private PayoutStatus status;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "amount_value"))
    @AttributeOverride(name = "currency", column = @Column(name = "amount_currency"))
    private Money amount;

    private String description;
}
