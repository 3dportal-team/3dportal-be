package ru.itis.tdportal.mainservice.models.exceptions;

import org.springframework.http.HttpStatus;
import ru.itis.tdportal.core.models.annotations.InternalError;
import ru.itis.tdportal.core.models.exceptions.PortalInternalException;

@InternalError(httpStatus = HttpStatus.BAD_REQUEST)
public class ModelFileIncorrectException extends PortalInternalException {

    public ModelFileIncorrectException(String message) {
        super(message);
    }
}
