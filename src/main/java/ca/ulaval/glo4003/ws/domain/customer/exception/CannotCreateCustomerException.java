package ca.ulaval.glo4003.ws.domain.customer.exception;

public abstract class CannotCreateCustomerException extends Exception {

    public CannotCreateCustomerException(String message) {
        super(message);
    }

    public abstract int getStatusCode();

}
