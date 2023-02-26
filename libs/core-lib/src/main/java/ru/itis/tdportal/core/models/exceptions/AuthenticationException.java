package ru.itis.tdportal.core.models.exceptions;

import org.springframework.http.HttpStatus;
import ru.itis.tdportal.core.models.annotations.InternalError;

@InternalError(httpStatus = HttpStatus.UNAUTHORIZED)
public class AuthenticationException extends PortalInternalException {

    public AuthenticationException(String message) {
        super(message);
    }
}
