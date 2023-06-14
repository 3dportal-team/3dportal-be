package ru.itis.tdportal.paymentservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BankCardDto {

    @JsonProperty("card_type")
    private String cardType;
    private String first6;

    // ISO 3166-1 alpha-2
    @JsonProperty("issuer_country")
    private String issuerCountry;

    @JsonProperty("issuer_name")
    private String issuerName;
    private String last4;

    @JsonProperty(value = "payout_token")
    private String payoutToken;
}
