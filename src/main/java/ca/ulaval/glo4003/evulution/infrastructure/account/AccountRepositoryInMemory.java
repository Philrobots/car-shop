package ca.ulaval.glo4003.evulution.infrastructure.account;

import ca.ulaval.glo4003.evulution.domain.account.Account;
import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.account.AccountRepository;
import ca.ulaval.glo4003.evulution.infrastructure.account.exceptions.AccountAlreadyExistsException;
import ca.ulaval.glo4003.evulution.infrastructure.account.exceptions.AccountNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class AccountRepositoryInMemory implements AccountRepository {
    private Map<AccountId, Account> accounts = new HashMap<>();

    @Override
    public void addAccount(Account account) throws AccountAlreadyExistsException {
        for (Account storedAccount : this.accounts.values()) {
            if (storedAccount.getEmail().equals(account.getEmail())) {
                throw new AccountAlreadyExistsException();
            }
        }

        this.accounts.put(account.getId(), account);
    }

    @Override
    public Account findAccountByEmail(String email) throws AccountNotFoundException {
        for (Account account : this.accounts.values()) {
            if (account.getEmail().equals(email)) {
                return account;
            }
        }
        throw new AccountNotFoundException();
    }

    @Override
    public Account getAccount(AccountId accountId) throws AccountNotFoundException {
        Account account = this.accounts.get(accountId);
        if (account == null) {
            throw new AccountNotFoundException();
        }
        return account;
    }
}
