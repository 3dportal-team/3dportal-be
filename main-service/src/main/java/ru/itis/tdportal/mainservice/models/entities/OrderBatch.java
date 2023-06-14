package ru.itis.tdportal.mainservice.models.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.NotAudited;
import ru.itis.tdportal.common.models.entities.Money;
import ru.itis.tdportal.core.models.entities.Audit;
import ru.itis.tdportal.mainservice.models.enums.OrderBatchStatus;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "order_batch")
public class OrderBatch extends Audit<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotAudited
    @OneToMany(mappedBy = "id.orderBatch", cascade = CascadeType.ALL)
    private List<OrderBatchItem> orderBatchItems;

    @Enumerated(EnumType.STRING)
    private OrderBatchStatus status;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "total_price_value"))
    @AttributeOverride(name = "currency", column = @Column(name = "total_price_currency"))
    private Money price;

    private UUID uuid;

}
