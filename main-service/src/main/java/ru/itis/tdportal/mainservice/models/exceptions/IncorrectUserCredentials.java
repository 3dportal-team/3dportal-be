package ru.itis.tdportal.mainservice.models.exceptions;

import org.springframework.http.HttpStatus;
import ru.itis.tdportal.core.models.annotations.InternalError;
import ru.itis.tdportal.core.models.exceptions.PortalInternalException;

@InternalError(httpStatus = HttpStatus.UNAUTHORIZED)
public class IncorrectUserCredentials extends PortalInternalException {

    public IncorrectUserCredentials(String message) {
        super(message);
    }
}
