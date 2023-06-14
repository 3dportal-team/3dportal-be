package ru.itis.tdportal.common.clients.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.itis.tdportal.common.clients.dtos.ErrorDto;
import ru.itis.tdportal.common.clients.exceptions.ExternalException;

import java.time.Instant;

@Slf4j
@ControllerAdvice
public class ExternalExceptionHandler {

    @ExceptionHandler(ExternalException.class)
    public ResponseEntity<ErrorDto> handle(ExternalException e) {
        log.error(e.getMessage());
        ErrorDto error = ErrorDto.builder()
                .message("Internal server error")
                .timestamp(Instant.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
