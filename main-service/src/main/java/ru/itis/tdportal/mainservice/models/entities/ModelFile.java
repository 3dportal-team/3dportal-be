package ru.itis.tdportal.mainservice.models.entities;

import lombok.*;
import ru.itis.tdportal.core.models.entities.Audit;
import ru.itis.tdportal.payment.models.models.Money;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString(exclude = "ownerId") // TODO: это зачем?
public class ModelFile extends Audit<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String givenName;
    private String description;
    private String originalFileName;
    private String generatedName;
    private String entityTag;
    private String mimeType;


    @Column(length = 150000)
    private String previewImageUrl;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private PortalUser owner;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "price_value"))
    @AttributeOverride(name = "currency", column = @Column(name = "price_currency"))
    private Money price;
}
