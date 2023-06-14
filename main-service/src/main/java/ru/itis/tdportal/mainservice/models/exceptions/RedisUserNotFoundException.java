package ru.itis.tdportal.mainservice.models.exceptions;

import org.springframework.http.HttpStatus;
import ru.itis.tdportal.core.models.annotations.InternalError;
import ru.itis.tdportal.core.models.exceptions.PortalInternalException;

@InternalError(httpStatus = HttpStatus.NOT_FOUND)
public class RedisUserNotFoundException extends PortalInternalException {

    public RedisUserNotFoundException(String message) {
        super(message);
    }
}
