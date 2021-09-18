package ca.ulaval.glo4003.evulution.domain.login;

public class NoAccountFoundException extends Exception {

    private int statusCode = 400;

    public NoAccountFoundException(String message) {
        super(message);
    }

    public int getStatusCode() {
        return this.statusCode;
    }

}
