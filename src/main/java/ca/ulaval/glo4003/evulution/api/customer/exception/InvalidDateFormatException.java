package ca.ulaval.glo4003.evulution.api.customer.exception;

import ca.ulaval.glo4003.evulution.exception.GenericException;

public class InvalidDateFormatException extends GenericException {
    private static final Integer ERROR_CODE = 400;
    private static final String ERROR_MESSAGE = "bad input parameter";

    public InvalidDateFormatException() {
        super(ERROR_CODE, ERROR_MESSAGE);
    }
}
