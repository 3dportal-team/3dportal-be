package ru.itis.tdportal.paymentservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.itis.tdportal.common.models.dtos.MoneyDto;

@Data
public class PayoutDto {

    private MoneyDto amount;

    @JsonProperty(value = "payout_token")
    private String payoutToken;
    private String description;
}
