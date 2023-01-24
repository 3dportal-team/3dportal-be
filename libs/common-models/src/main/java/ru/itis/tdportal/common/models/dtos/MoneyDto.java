package ru.itis.tdportal.common.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tdportal.common.models.enums.Currency;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyDto {

    private Double value;
    private Currency currency;
}
