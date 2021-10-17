package ca.ulaval.glo4003.evulution.domain.account.customer;

import ca.ulaval.glo4003.evulution.domain.account.Account;
import ca.ulaval.glo4003.evulution.domain.account.AccountRepository;
import ca.ulaval.glo4003.evulution.domain.account.exceptions.CustomerAlreadyExistsException;

public class AccountValidator {
    private final AccountRepository accountRepository;

    public AccountValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void validateEmailIsNotInUse(String email) throws CustomerAlreadyExistsException {
        Account account = this.accountRepository.getAccountByEmail(email);

        if (account != null) {
            throw new CustomerAlreadyExistsException();
        }
    }
}
