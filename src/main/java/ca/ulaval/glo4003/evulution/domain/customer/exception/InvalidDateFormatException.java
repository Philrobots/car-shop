package ca.ulaval.glo4003.evulution.domain.customer.exception;

import ca.ulaval.glo4003.evulution.exception.GenericException;

public class InvalidDateFormatException extends GenericException {
    private static int errorCode = 400;
    private static String errorMessage = "bad input parameter";

    public InvalidDateFormatException() {
        super(errorCode, errorMessage);
    }
}
