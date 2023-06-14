package ru.itis.tdportal.mainservice.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationType {

    TO_SELLER_MODEL_PURCHASED("Модель №%s приобретена пользователем");

    private final String message;
}
