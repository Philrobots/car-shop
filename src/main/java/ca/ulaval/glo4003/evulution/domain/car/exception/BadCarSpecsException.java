package ca.ulaval.glo4003.evulution.domain.car.exception;

import ca.ulaval.glo4003.evulution.exception.GenericException;

public class BadCarSpecsException extends GenericException {
    private static int errorCode = 400;
    private static String errorMessage = "bad input parameter";

    public BadCarSpecsException() {
        super(errorCode, errorMessage);
    }
}
