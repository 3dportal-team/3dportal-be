package ru.itis.tdportal.paymentservice.dtos;

import lombok.Data;
import ru.itis.tdportal.common.models.dtos.MoneyDto;

@Data
public class PayoutDto {

    private MoneyDto amount;
    private String paymentMethodId;
    private String description;

}
