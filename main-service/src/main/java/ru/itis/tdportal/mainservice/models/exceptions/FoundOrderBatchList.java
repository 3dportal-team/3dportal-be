package ru.itis.tdportal.mainservice.models.exceptions;

import ru.itis.tdportal.core.models.exceptions.PortalInternalException;

public class FoundOrderBatchList extends PortalInternalException {

    public FoundOrderBatchList(String message) {
        super(message);
    }
}
