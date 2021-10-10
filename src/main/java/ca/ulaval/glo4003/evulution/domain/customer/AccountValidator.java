package ca.ulaval.glo4003.evulution.domain.customer;

import ca.ulaval.glo4003.evulution.domain.account.Account;
import ca.ulaval.glo4003.evulution.domain.account.AccountRepository;
import ca.ulaval.glo4003.evulution.domain.customer.exception.CustomerAlreadyExistsException;

public class AccountValidator {
    private final AccountRepository accountRepository;

    public AccountValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void validateEmailIsNotInUse(String email) throws CustomerAlreadyExistsException {
        Account c = this.accountRepository.getAccountByEmail(email);

        if (c != null) {
            throw new CustomerAlreadyExistsException();
        }
    }
}
