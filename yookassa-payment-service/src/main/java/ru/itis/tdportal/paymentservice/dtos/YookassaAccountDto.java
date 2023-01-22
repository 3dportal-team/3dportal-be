package ru.itis.tdportal.paymentservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.itis.tdportal.paymentservice.models.enums.PaymentMethodData;

import java.util.List;

@Data
public class YookassaAccountDto {

    @JsonProperty("payment_methods")
    private List<PaymentMethodData>  paymentMethods;

}
