package ru.itis.tdportal.paymentservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.itis.tdportal.common.models.dtos.PaymentDto;

import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreatedPaymentDto extends PaymentDto {

    @JsonProperty("id")
    private UUID yooId;

    private Map<String, String> confirmation;

    private Boolean capture;
}
