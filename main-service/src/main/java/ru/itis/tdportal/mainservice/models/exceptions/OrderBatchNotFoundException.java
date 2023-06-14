package ru.itis.tdportal.mainservice.models.exceptions;

import org.springframework.http.HttpStatus;
import ru.itis.tdportal.core.models.annotations.InternalError;
import ru.itis.tdportal.core.models.exceptions.PortalInternalException;

@InternalError(httpStatus = HttpStatus.NOT_FOUND)
public class OrderBatchNotFoundException extends PortalInternalException {

    public OrderBatchNotFoundException(String message) {
        super(message);
    }
}
