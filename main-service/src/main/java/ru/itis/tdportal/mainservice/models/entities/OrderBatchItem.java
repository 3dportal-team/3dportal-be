package ru.itis.tdportal.mainservice.models.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "order_batch_item")
public class OrderBatchItem {

    @EmbeddedId
    private OrderBatchItemID id;
}
