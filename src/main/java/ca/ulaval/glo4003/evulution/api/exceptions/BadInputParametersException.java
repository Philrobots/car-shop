package ca.ulaval.glo4003.evulution.api.exceptions;

import ca.ulaval.glo4003.evulution.exception.GenericException;

public class BadInputParametersException extends GenericException {
    private static final Integer ERROR_CODE = 400;
    private static final String ERROR_MESSAGE = "bad input parameter";

    public BadInputParametersException() {
        super(ERROR_CODE, ERROR_MESSAGE);
    }
}
