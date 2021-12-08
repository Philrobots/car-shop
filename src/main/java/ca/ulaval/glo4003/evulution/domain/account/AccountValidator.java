package ca.ulaval.glo4003.evulution.domain.account;

import ca.ulaval.glo4003.evulution.domain.account.exceptions.UserIsNotAdminException;
import ca.ulaval.glo4003.evulution.infrastructure.account.exceptions.AccountNotFoundException;

public class AccountValidator {
    private final AccountRepository accountRepository;

    public AccountValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void validateAdminAccount(AccountId accountId) throws AccountNotFoundException, UserIsNotAdminException {
        Account account = this.accountRepository.getAccount(accountId);
        account.verifyIfAdmin();
    }
}
