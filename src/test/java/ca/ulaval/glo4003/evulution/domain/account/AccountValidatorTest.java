package ca.ulaval.glo4003.evulution.domain.account;

import ca.ulaval.glo4003.evulution.domain.account.exceptions.UserIsNotAdminException;
import ca.ulaval.glo4003.evulution.infrastructure.account.exceptions.AccountNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountValidatorTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountId accountId;

    @Mock
    private Account account;

    private AccountValidator accountValidator;

    @BeforeEach void setUp() {
        this.accountValidator = new AccountValidator(accountRepository);
    }

    @Test
    public void whenValidateAdminAccount_thenAccountRepositoryGetAccount() throws
        AccountNotFoundException, UserIsNotAdminException {
        // given
        BDDMockito.given(accountRepository.getAccount(accountId)).willReturn(account);

        // when
        this.accountValidator.validateAdminAccount(accountId);

        // then
        Mockito.verify(accountRepository).getAccount(accountId);
    }

    @Test
    public void whenValidateAdminAccount_thenAccountVerifiesIfAdmin() throws
        AccountNotFoundException, UserIsNotAdminException {
        // given
        BDDMockito.given(accountRepository.getAccount(accountId)).willReturn(account);

        // when
        this.accountValidator.validateAdminAccount(accountId);

        // then
        Mockito.verify(account).verifyIfAdmin();
    }

}
