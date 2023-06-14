package ru.itis.tdportal.common.clients.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ErrorDto {

    private String message;
    private Instant timestamp;

}
