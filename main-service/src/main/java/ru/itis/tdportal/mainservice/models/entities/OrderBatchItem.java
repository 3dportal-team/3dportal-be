package ru.itis.tdportal.mainservice.models.entities;

import lombok.Getter;
import lombok.Setter;
import ru.itis.tdportal.payment.models.models.Money;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "order_batch_item")
public class OrderBatchItem {

    @EmbeddedId
    private OrderBatchItemID id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "price_value"))
    @AttributeOverride(name = "currency", column = @Column(name = "price_currency"))
    private Money price;
}
