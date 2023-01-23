package ru.itis.tdportal.mainservice.models.exceptions;

import ru.itis.tdportal.core.models.exceptions.PortalInternalException;

public class ModelFileNotFoundException extends PortalInternalException {

    public ModelFileNotFoundException(String message) {
        super(message);
    }
}
