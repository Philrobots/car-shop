package ca.ulaval.glo4003.evulution.domain.account;

import ca.ulaval.glo4003.evulution.infrastructure.account.exceptions.AccountAlreadyExistsException;
import ca.ulaval.glo4003.evulution.infrastructure.account.exceptions.AccountNotFoundException;

public interface AccountRepository {
    void addAccount(Account account) throws AccountAlreadyExistsException;

    Account findAccountByEmail(String email) throws AccountNotFoundException;

    Account getAccount(AccountId accountId) throws AccountNotFoundException;
}
