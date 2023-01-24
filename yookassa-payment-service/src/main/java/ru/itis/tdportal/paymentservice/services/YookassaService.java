package ru.itis.tdportal.paymentservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import ru.itis.tdportal.common.clients.constants.HttpHeader;
import ru.itis.tdportal.common.clients.custom.HttpClient;
import ru.itis.tdportal.common.clients.custom.HttpRequest;
import ru.itis.tdportal.common.models.dtos.PaymentDto;
import ru.itis.tdportal.paymentservice.dtos.CreatedPaymentDto;
import ru.itis.tdportal.paymentservice.dtos.YookassaAccountDto;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class YookassaService {

    private final HttpClient httpClient;

    @Value("${yookassa.api.uri}")
    private String yooKassaApiUri;

    @Value("${yookassa.api.shop.id}")
    private String shopId;

    @Value("${yookassa.api.secret-key}")
    private String secretKey;

    public YookassaAccountDto getAccountInfo() {
        HttpRequestBase request = HttpRequest.builder()
                .method(HttpMethod.GET)
                .url(yooKassaApiUri + "/me")
                .baseAuth(shopId, secretKey)
                .build();

        return (YookassaAccountDto) httpClient.sendRequest(YookassaAccountDto.class, request);
    }

    public CreatedPaymentDto createPayment(PaymentDto dto, UUID idempotenceKey) {
        StringEntity entity = httpClient.parseRequestContent(dto);

        HttpRequestBase request = HttpRequest.builder()
                .method(HttpMethod.POST)
                .url(yooKassaApiUri + "/payments")
                .baseAuth(shopId, secretKey)
                .json(entity)
                .setHeader(HttpHeader.IdempotenceKey, idempotenceKey.toString())
                .build();

        return (CreatedPaymentDto) httpClient.sendRequest(CreatedPaymentDto.class, request);
    }

}