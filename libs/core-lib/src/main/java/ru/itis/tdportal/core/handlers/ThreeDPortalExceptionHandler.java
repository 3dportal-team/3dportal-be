package ru.itis.tdportal.core.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.itis.tdportal.core.dtos.ErrorDto;
import ru.itis.tdportal.core.models.annotations.InternalError;
import ru.itis.tdportal.core.models.exceptions.PortalInternalException;

import java.time.Instant;

@Slf4j
@ControllerAdvice
public class ThreeDPortalExceptionHandler {

    @ExceptionHandler(PortalInternalException.class)
    public ResponseEntity<ErrorDto> handle(PortalInternalException e) {
        HttpStatus status = e.getClass()
                .getAnnotation(InternalError.class)
                .httpStatus();
        log.error(e.getMessage());

        ErrorDto error = ErrorDto.builder()
                .message(e.getMessage())
                .timestamp(Instant.now())
                .build();

        return new ResponseEntity<>(error, status);
    }
}
