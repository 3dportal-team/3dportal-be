package ru.itis.tdportal.mainservice.models.exceptions;

import ru.itis.tdportal.core.models.exceptions.PortalInternalException;

public class ModelFileIncorrectException extends PortalInternalException {

    public ModelFileIncorrectException(String message) {
        super(message);
    }
}
