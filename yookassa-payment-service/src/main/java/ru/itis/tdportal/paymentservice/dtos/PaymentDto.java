package ru.itis.tdportal.paymentservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.itis.tdportal.payment.models.dtos.MoneyDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    private MoneyDto amount;

    @JsonProperty("payment_method_data")
    private PaymentMethodDataDto paymentMethodData;

    private PaymentConfirmationDto confirmation;

    private String description;
}
