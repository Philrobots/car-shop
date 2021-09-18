package ca.ulaval.glo4003.evulution.domain.customer.exception;

public abstract class CannotCreateCustomerException extends Exception {

    public CannotCreateCustomerException(String message) {
        super(message);
    }

    public abstract int getStatusCode();

}
