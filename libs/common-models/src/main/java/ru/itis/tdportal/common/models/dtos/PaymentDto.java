package ru.itis.tdportal.common.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tdportal.common.models.enums.PaymentStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    private PaymentStatus status;

    private String description;

    private MoneyDto amount;

    private Boolean paid;

    private Long receiverId; // FIXME: 24.04.2023
}
