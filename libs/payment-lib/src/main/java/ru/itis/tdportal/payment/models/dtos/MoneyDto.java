package ru.itis.tdportal.payment.models.dtos;

import lombok.*;
import ru.itis.tdportal.payment.models.enums.Currency;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyDto {

    private Double value;
    private Currency currency;
}
