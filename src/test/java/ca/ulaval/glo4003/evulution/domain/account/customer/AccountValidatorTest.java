package ca.ulaval.glo4003.evulution.domain.account.customer;

import ca.ulaval.glo4003.evulution.domain.account.Account;
import ca.ulaval.glo4003.evulution.domain.account.AccountRepository;
import ca.ulaval.glo4003.evulution.domain.account.exceptions.CustomerAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    public void givenUsedEmail_whenValidateEmailIsNotInUse_thenShouldThrowCustomerAlreadyExistsException() {
        // given
        BDDMockito.given(accountRepository.getAccountByEmail(A_EMAIL)).willReturn(account);

        // when
        Executable validateEmailIsNotInUse = () -> this.accountValidator.validateEmailIsNotInUse(A_EMAIL);

        // then
        assertThrows(CustomerAlreadyExistsException.class, validateEmailIsNotInUse);
    }

    @Test
    public void whenValidateEmailIsNotInUse_thenShouldNotThrow() {
        // when
        Executable validateEmailIsNotInUse = () -> this.accountValidator.validateEmailIsNotInUse(A_EMAIL);

        // then
        assertDoesNotThrow(validateEmailIsNotInUse);
    }
}
