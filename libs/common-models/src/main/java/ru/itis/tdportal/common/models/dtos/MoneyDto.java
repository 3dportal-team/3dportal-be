package ru.itis.tdportal.common.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tdportal.common.models.enums.Currency;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyDto {

    private BigDecimal value; // FIXME: 24.04.2023
    private Currency currency;
}
