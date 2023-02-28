package ru.itis.tdportal.common.clients.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.tdportal.common.clients.constants.HttpHeader;
import ru.itis.tdportal.common.models.dtos.PaymentDto;

import java.util.UUID;

@FeignClient(name = "yoo-payment-service", url = "http://localhost:8082/internal") // TODO: убрать
public interface YooPaymentServiceClient {

    @RequestMapping(method = RequestMethod.POST, value = "/api/payment")
    Object createPayment(@RequestHeader(HttpHeader.IdempotenceKey) UUID idempotenceKey, PaymentDto dto);
}


