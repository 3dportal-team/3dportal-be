package ru.itis.tdportal.core.models.exceptions;

import ru.itis.tdportal.core.models.annotations.InternalError;

@InternalError
public class PortalInternalException extends RuntimeException {

    public PortalInternalException(String message) {
        super(message);
    }

}
