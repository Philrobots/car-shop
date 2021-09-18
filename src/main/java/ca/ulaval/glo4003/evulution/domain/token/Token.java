package ca.ulaval.glo4003.evulution.domain.token;

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
}
