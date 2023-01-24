package ru.itis.tdportal.paymentservice.models.exceptions;

import ru.itis.tdportal.core.models.exceptions.PortalInternalException;

public class PaymentNotFoundException extends PortalInternalException {

    public PaymentNotFoundException(String message) {
        super(message);
    }
}
