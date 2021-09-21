package ca.ulaval.glo4003.evulution.api.customer.exception;

import ca.ulaval.glo4003.evulution.exception.GenericException;

public class InvalidDateFormatException extends GenericException {
    private static Integer ERROR_CODE = 400;
    private static String ERROR_MESSAGE = "bad input parameter";

    public InvalidDateFormatException() {
        super(ERROR_CODE, ERROR_MESSAGE);
    }
}
