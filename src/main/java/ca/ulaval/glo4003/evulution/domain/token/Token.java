package ca.ulaval.glo4003.evulution.domain.token;

import java.util.Objects;
import java.util.UUID;

public class Token {

    private String token;

    private boolean isAdmin = false;

    public Token(boolean isAdmin) {
        this.token = UUID.randomUUID().toString();
        this.isAdmin = isAdmin;
    }

    public Token(String uuid) {
        this.token = uuid;
    }

    public String getToken() {
        return token;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Token))
            return false;
        Token token1 = (Token) o;
        return Objects.equals(token, token1.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }
}
