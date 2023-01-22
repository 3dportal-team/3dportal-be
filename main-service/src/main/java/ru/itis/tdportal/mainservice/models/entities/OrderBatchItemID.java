package ru.itis.tdportal.mainservice.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class OrderBatchItemID implements Serializable {

    @ManyToOne(cascade = CascadeType.ALL) // TODO: проработать каскады (не только тут, но и в других сущностях)
    @JoinColumn(name = "order_id")
    private OrderBatch orderBatch;

    @ManyToOne
    @JoinColumn(name = "model_file_id")
    private ModelFile modelFile;
}