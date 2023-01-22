package ru.itis.tdportal.paymentservice.dtos.embded;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ConfirmationDto {

    private String type = "redirect";

    @JsonProperty("return_url")
    private String returnUrl = "https://www.google.com/";
}
