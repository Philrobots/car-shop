package ca.ulaval.glo4003.evulution.infrastructure.account;

import ca.ulaval.glo4003.evulution.domain.account.Account;
import ca.ulaval.glo4003.evulution.domain.account.AccountRepository;

import java.util.HashMap;
import java.util.Map;

public class AccountRepositoryInMemory implements AccountRepository {
    private Map<String, Account> accounts = new HashMap<String, Account>();

    @Override
    public void addAccount(Account account) {
        accounts.put(account.getEmail(), account);
    }

    @Override
    public Account getAccountByEmail(String email) {
        return accounts.get(email);
    }
}
