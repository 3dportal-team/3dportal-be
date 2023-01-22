package ru.itis.tdportal.mainservice.models.exceptions;

import ru.itis.tdportal.core.models.exceptions.PortalInternalException;

public class ModelFileNotFound extends PortalInternalException {

    public ModelFileNotFound(String message) {
        super(message);
    }
}
