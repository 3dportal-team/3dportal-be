package ru.itis.tdportal.mainservice.models.exceptions;

import org.springframework.http.HttpStatus;
import ru.itis.tdportal.core.models.annotations.InternalError;
import ru.itis.tdportal.core.models.exceptions.PortalInternalException;

@InternalError(httpStatus = HttpStatus.CONFLICT)
public class ModelFileIsFreeException extends PortalInternalException {

    public ModelFileIsFreeException(String message) {
        super(message);
    }
}
