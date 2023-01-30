package ru.itis.tdportal.common.clients.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.tdportal.common.clients.constants.HttpHeader;
import ru.itis.tdportal.common.models.enums.PaymentStatus;

import java.util.UUID;

@FeignClient(name = "main-service", url = "http://localhost:8081")
public interface MainServiceClient {

    @RequestMapping(method = RequestMethod.POST, value = "/api/order")
    void updatePaymentStatusNotification(@RequestHeader(HttpHeader.IdempotenceKey) UUID idempotenceKey,
                                         @RequestParam PaymentStatus status);
}
