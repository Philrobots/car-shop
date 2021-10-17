package ca.ulaval.glo4003.evulution.infrastructure.token;

import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.domain.token.exceptions.UnauthorizedRequestException;
import ca.ulaval.glo4003.evulution.service.authorization.TokenRepository;

import java.util.HashMap;

public class TokenRepositoryInMemory implements TokenRepository {
    private final HashMap<Token, String> tokens = new HashMap<>();

    public void addTokenWithEmail(Token token, String email) {
        this.tokens.put(token, email);
    }

    public String getEmail(Token token) {
        return tokens.get(token);
    }

    public void validateToken(Token token) {
        if (!tokens.containsKey(token))
            throw new UnauthorizedRequestException();
    }
}
