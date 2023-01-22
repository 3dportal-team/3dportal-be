package ru.itis.tdportal.paymentservice.dtos;

import lombok.*;
import ru.itis.tdportal.paymentservice.models.enums.PaymentMethodData;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodDataDto {

    private PaymentMethodData type;
}
