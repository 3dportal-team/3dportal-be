package ru.itis.tdportal.paymentservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.itis.tdportal.common.models.dtos.PaymentDto;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreatedPaymentDto extends PaymentDto {

    private Map<String, String> confirmation;

}
