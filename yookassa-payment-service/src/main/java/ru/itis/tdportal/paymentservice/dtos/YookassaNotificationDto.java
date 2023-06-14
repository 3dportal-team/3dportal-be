package ru.itis.tdportal.paymentservice.dtos;

import lombok.Data;
import ru.itis.tdportal.paymentservice.models.enums.YookassaEventType;

@Data
public class YookassaNotificationDto {

    private YookassaEventType event;
    private EventObjectDto object;
}
