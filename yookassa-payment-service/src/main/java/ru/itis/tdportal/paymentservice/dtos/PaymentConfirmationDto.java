package ru.itis.tdportal.paymentservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

// TODO: убрать хардкод
@Data
public class PaymentConfirmationDto {

    private String type = "redirect";

    @JsonProperty("return_url")
    private final String returnUrl = "https://www.google.com/";

    @JsonProperty("confirmation_url")
    private String confirmationUrl;
}
