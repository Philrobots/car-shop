package ca.ulaval.glo4003.evulution.exception;

public abstract class GenericException extends RuntimeException {
    private final Integer errorCode;
    private final String errorMessage;

    public GenericException(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
