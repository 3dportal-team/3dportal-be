package ru.itis.tdportal.paymentservice.models.exceptions;

public class ExternalException extends RuntimeException {

    public ExternalException(String message) {
        super(message);
    }

    public ExternalException(Exception cause) {
        super(cause);
    }
}
