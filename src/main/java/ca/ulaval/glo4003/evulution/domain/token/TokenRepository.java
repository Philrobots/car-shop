package ca.ulaval.glo4003.evulution.domain.token;

import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.infrastructure.token.exceptions.TokenNotFoundException;

public interface TokenRepository {

    void addToken(Token token, AccountId accountId);

    AccountId getAccountId(Token token) throws TokenNotFoundException;
}
