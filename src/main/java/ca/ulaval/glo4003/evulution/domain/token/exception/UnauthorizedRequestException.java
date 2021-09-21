package ca.ulaval.glo4003.evulution.domain.token.exception;

import ca.ulaval.glo4003.evulution.exception.GenericException;

public class UnauthorizedRequestException extends GenericException {
    private static final Integer ERROR_CODE = 404;
    private static final String ERROR_MESSAGE = "Unauthorized request";

    public UnauthorizedRequestException() {
        super(ERROR_CODE, ERROR_MESSAGE);
    }
}
