package ca.ulaval.glo4003.evulution.domain.account.customer;

import ca.ulaval.glo4003.evulution.domain.account.Account;
import ca.ulaval.glo4003.evulution.domain.account.AccountRepository;
import ca.ulaval.glo4003.evulution.domain.account.AccountValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountValidatorTest {
    private final String A_EMAIL = "test@live.fr";

    private AccountValidator accountValidator;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private Account account;

    @BeforeEach
    void setUp() {
        this.accountValidator = new AccountValidator(accountRepository);
    }

}
