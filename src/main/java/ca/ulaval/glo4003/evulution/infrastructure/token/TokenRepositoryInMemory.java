package ca.ulaval.glo4003.evulution.infrastructure.token;

import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.domain.token.TokenRepository;
import ca.ulaval.glo4003.evulution.infrastructure.token.exceptions.TokenNotFoundException;

import java.util.HashMap;

public class TokenRepositoryInMemory implements TokenRepository {
    private final HashMap<Token, AccountId> tokens = new HashMap<>();

    public void addToken(Token token, AccountId accountId) {
        this.tokens.put(token, accountId);
    }

    public AccountId getAccountId(Token token) throws TokenNotFoundException {
        AccountId accountId = this.tokens.get(token);
        if (accountId == null) {
            throw new TokenNotFoundException();
        }
        return accountId;
    }
}
