package ca.ulaval.glo4003.evulution.infrastructure.account;

import ca.ulaval.glo4003.evulution.domain.account.Account;
import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.infrastructure.account.exceptions.AccountAlreadyExistsException;
import ca.ulaval.glo4003.evulution.infrastructure.account.exceptions.AccountNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AccountRepositoryInMemoryTest {
    private static final String AN_EMAIL = "johndoe@email.com";
    private static final String ANOTHER_EMAIL = "youc@email.com";

    @Mock
    private Account account;

    @Mock
    private AccountId accountId;

    private AccountRepositoryInMemory accountRepositoryInMemory;

    @BeforeEach
    public void setUp() {
        this.accountRepositoryInMemory = new AccountRepositoryInMemory();
    }

    @Test
    public void givenExistingAccount_whenAddAccount_thenThrowAccountAlreadyExistsException()
        throws AccountAlreadyExistsException {
        // given
        BDDMockito.given(account.getEmail()).willReturn(ANOTHER_EMAIL);
        this.accountRepositoryInMemory.addAccount(account);

        // when
        Executable addAccount = () -> this.accountRepositoryInMemory.addAccount(account);

        // then
        Assertions.assertThrows(AccountAlreadyExistsException.class, addAccount);
    }

    @Test
    public void givenExistingAccount_whenFindAccountByEmail_thenReturnAccount()
        throws AccountAlreadyExistsException, AccountNotFoundException {
        // given
        BDDMockito.given(account.getEmail()).willReturn(AN_EMAIL);
        this.accountRepositoryInMemory.addAccount(account);

        // when
        Account accountRequest = this.accountRepositoryInMemory.findAccountByEmail(AN_EMAIL);

        // then
        assertEquals(accountRequest, account);
    }

    @Test
    public void givenNoExistingAccount_whenFindAccountByEmail_thenThrowAccountNotFoundException()
        throws AccountAlreadyExistsException {
        // given
        BDDMockito.given(account.getEmail()).willReturn(ANOTHER_EMAIL);
        this.accountRepositoryInMemory.addAccount(account);

        // when
        Executable findAccountByEmail = () -> this.accountRepositoryInMemory.findAccountByEmail(AN_EMAIL);

        // then
        Assertions.assertThrows(AccountNotFoundException.class, findAccountByEmail);
    }

    @Test
    public void givenExistingAccount_whenGetAccount_thenReturnAccount()
            throws AccountAlreadyExistsException, AccountNotFoundException {
        // given
        BDDMockito.given(account.getId()).willReturn(accountId);
        this.accountRepositoryInMemory.addAccount(account);

        // when
        Account customer = this.accountRepositoryInMemory.getAccount(accountId);

        // then
        assertEquals(customer, account);
    }


    @Test
    public void givenNoExistingAccount_whenGetAccount_thenThrowAccountNotFoundException() {
        // when
        Executable getAccount = () -> this.accountRepositoryInMemory.getAccount(accountId);

        // then
        Assertions.assertThrows(AccountNotFoundException.class, getAccount);
    }


}
