package ru.itis.tdportal.paymentservice.models.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ru.itis.tdportal.core.models.entities.Audit;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "bank_card")
public class BankCard extends Audit<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("card_type")
    private String cardType;
    private String first6;

    // ISO 3166-1 alpha-2
    @Column(name = "issuer_country")
    private String issuerCountry;

    @Column(name = "issuer_name")
    private String issuerName;
    private String last4;

    @Column(name = "payout_token")
    private String payoutToken;

    @OneToMany(mappedBy = "receiver")
    private Long creatorId;
}
