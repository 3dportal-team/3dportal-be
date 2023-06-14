package ru.itis.tdportal.mainservice.models.exceptions;

import org.springframework.http.HttpStatus;
import ru.itis.tdportal.core.models.annotations.InternalError;
import ru.itis.tdportal.core.models.exceptions.PortalInternalException;

@InternalError(httpStatus = HttpStatus.INTERNAL_SERVER_ERROR)
public class IncorrectModelFileException extends PortalInternalException {

    public IncorrectModelFileException(String message) {
        super(message);
    }
}
