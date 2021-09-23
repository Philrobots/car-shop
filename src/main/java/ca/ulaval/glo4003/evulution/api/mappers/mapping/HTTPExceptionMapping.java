package ca.ulaval.glo4003.evulution.api.mappers.mapping;

public class HTTPExceptionMapping {
    private int statusCode;
    private String errorMessage;

    public HTTPExceptionMapping(int statusCode, String errorMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
