package ru.itis.tdportal.mainservice.models.exceptions;

import ru.itis.tdportal.core.models.exceptions.PortalInternalException;

public class OrderBatchNotFoundException extends PortalInternalException {

    public OrderBatchNotFoundException(String message) {
        super(message);
    }
}
