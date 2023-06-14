package ru.itis.tdportal.paymentservice.models.entities;

import lombok.Getter;
import lombok.Setter;
import ru.itis.tdportal.common.models.entities.Money;
import ru.itis.tdportal.common.models.enums.PaymentStatus;

import javax.persistence.*;
import java.util.UUID;

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

    private String description;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "amount_value"))
    @AttributeOverride(name = "currency", column = @Column(name = "amount_currency"))
    private Money amount;

    private Boolean paid;

    @Column(name = "idempotence_key")
    private UUID idempotenceKey;

    @Column(name = "yoo_id")
    private UUID yooId;

    @ManyToOne
    @JoinColumn(name = "bank_card_id")
    private BankCard bankCard;
}
