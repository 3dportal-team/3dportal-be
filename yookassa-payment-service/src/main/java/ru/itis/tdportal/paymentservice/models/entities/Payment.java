package ru.itis.tdportal.paymentservice.models.entities;

import lombok.Getter;
import lombok.Setter;
import ru.itis.tdportal.payment.models.models.Money;
import ru.itis.tdportal.paymentservice.models.enums.PaymentStatus;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private PaymentStatus status;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "amount_value"))
    @AttributeOverride(name = "currency", column = @Column(name = "amount_currency"))
    private Money amount;

    private Boolean paid;

    @Column(name = "expires_at")
    private Instant expiresAt;

    private Boolean refundable;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "income_value"))
    @AttributeOverride(name = "currency", column = @Column(name = "income_currency"))
    private Money incomeAmount;

    private String description;
}
