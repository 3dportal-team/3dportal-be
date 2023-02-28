package ru.itis.tdportal.paymentservice.controllers.external;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.tdportal.paymentservice.dtos.YookassaNotificationDto;
import ru.itis.tdportal.paymentservice.services.PaymentService;

@Hidden
@RestController
@RequiredArgsConstructor
@RequestMapping("/external/api/payment")
public class PaymentExternalController {

    private final PaymentService paymentService;

    @PostMapping("/notify")
    // TODO: добавить проверку адресов
    public void movePaymentToStatus(@RequestBody YookassaNotificationDto dto) {
        paymentService.movePaymentToStatusByEvent(dto); // TODO: может быть refund
    }
}
