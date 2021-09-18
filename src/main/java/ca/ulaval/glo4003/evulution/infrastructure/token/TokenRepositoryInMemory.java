package ca.ulaval.glo4003.evulution.infrastructure.token;

import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.domain.token.TokenFactory;
import ca.ulaval.glo4003.evulution.service.login.TokenRepository;

import java.util.HashMap;
import java.util.Map;

public class TokenRepositoryInMemory implements TokenRepository {

    private TokenFactory tokenFactory;

    private Map<String, Token> tokens = new HashMap<String, Token>();

    public TokenRepositoryInMemory(TokenFactory tokenFactory) {
        this.tokenFactory = tokenFactory;
    }

    public Map<String, Token> getTokens() {
        return tokens;
    }

    @Override
    public Token loginCustomer(String email) {
        Token token = tokenFactory.generateNewToken();
        this.tokens.put(email, token);
        return token;
    }

    public void signOutCustomer(String email) {
        this.tokens.remove(email);
    }
}
