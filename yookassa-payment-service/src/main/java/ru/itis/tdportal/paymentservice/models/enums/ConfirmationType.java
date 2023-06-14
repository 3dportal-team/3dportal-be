package ru.itis.tdportal.paymentservice.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  ConfirmationType {
    
    REDIRECT("redirect");
    
    @JsonValue
    private String value;
}
