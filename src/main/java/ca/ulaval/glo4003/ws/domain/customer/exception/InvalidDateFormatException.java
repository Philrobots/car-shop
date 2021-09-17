package ca.ulaval.glo4003.ws.domain.customer.exception;

public class InvalidDateFormatException extends CannotCreateCustomerException {

    public InvalidDateFormatException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
