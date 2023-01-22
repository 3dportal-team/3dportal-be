package ru.itis.tdportal.mainservice.models.exceptions;

import ru.itis.tdportal.core.models.exceptions.PortalInternalException;

public class RedisUserNotFoundException extends PortalInternalException {

    public RedisUserNotFoundException(String message) {
        super(message);
    }
}
