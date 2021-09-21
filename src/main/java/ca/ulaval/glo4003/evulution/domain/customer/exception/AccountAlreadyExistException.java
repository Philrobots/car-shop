package ca.ulaval.glo4003.evulution.domain.customer.exception;

import ca.ulaval.glo4003.evulution.exception.GenericException;

public class AccountAlreadyExistException extends GenericException {
    private static int errorCode = 409;
    private static String errorMessage = "an existing customer already exists";

    public AccountAlreadyExistException() {
        super(errorCode, errorMessage);
    }
}
