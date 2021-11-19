package ca.ulaval.glo4003.evulution.domain.token;

public class TokenFactory {

    public Token generateNewToken(boolean isAdmin) {
        return new Token(isAdmin);
    }

}
