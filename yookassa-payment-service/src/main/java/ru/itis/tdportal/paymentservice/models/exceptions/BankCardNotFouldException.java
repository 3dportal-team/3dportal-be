package ru.itis.tdportal.paymentservice.models.exceptions;

import org.springframework.http.HttpStatus;
import ru.itis.tdportal.core.models.annotations.InternalError;
import ru.itis.tdportal.core.models.exceptions.PortalInternalException;

@InternalError(httpStatus = HttpStatus.UNAUTHORIZED)
public class BankCardNotFouldException extends PortalInternalException {

    public BankCardNotFouldException(String message) {
        super(message);
    }
}
