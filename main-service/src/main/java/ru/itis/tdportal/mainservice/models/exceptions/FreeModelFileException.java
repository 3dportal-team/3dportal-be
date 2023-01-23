package ru.itis.tdportal.mainservice.models.exceptions;

import ru.itis.tdportal.core.models.exceptions.PortalInternalException;

public class FreeModelFileException extends PortalInternalException {

    public FreeModelFileException(String message) {
        super(message);
    }
}
