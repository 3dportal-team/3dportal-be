package ru.itis.tdportal.paymentservice.models.exceptions;

import org.springframework.http.HttpStatus;
import ru.itis.tdportal.core.models.annotations.InternalError;
import ru.itis.tdportal.core.models.exceptions.PortalInternalException;

@InternalError(httpStatus = HttpStatus.NOT_FOUND)
public class PaymentNotFoundException extends PortalInternalException {

    public PaymentNotFoundException(String message) {
        super(message);
    }
}
