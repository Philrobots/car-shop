package ca.ulaval.glo4003.evulution.domain.token;

import java.util.Objects;
import java.util.UUID;

public class Token {

    private String token;

    public Token() {
        this.token = UUID.randomUUID().toString();
    }

    public Token(String uuid) {
        this.token = uuid;
    }

    public String getToken() {
        return token;
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
