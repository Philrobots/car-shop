package ca.ulaval.glo4003.ws.domain.customer.exception;

public class AccountAlreadyExistException extends CannotCreateCustomerException {

    public AccountAlreadyExistException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 409;
    }
}
