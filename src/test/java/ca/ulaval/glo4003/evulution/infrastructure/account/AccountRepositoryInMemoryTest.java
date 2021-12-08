package ca.ulaval.glo4003.evulution.infrastructure.account;

import ca.ulaval.glo4003.evulution.domain.account.Account;
import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.account.manager.Manager;
import ca.ulaval.glo4003.evulution.infrastructure.account.exceptions.AccountAlreadyExistsException;
import ca.ulaval.glo4003.evulution.infrastructure.account.exceptions.AccountNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AccountRepositoryInMemoryTest {
    private static final String AN_EMAIL = "johndoe@email.com";

    @Mock
    private Account account;

    @Mock
    private Manager manager;

    @Mock
    private AccountId accountId;

    private AccountRepositoryInMemory accountRepositoryInMemory;

    @BeforeEach
    public void setUp() {
        this.accountRepositoryInMemory = new AccountRepositoryInMemory();
    }

    @Test
    public void givenCustomerAccount_whenAddAccount_thenAccountRepositoryInMemoryContainsAccount()
        throws AccountAlreadyExistsException, AccountNotFoundException {
        // given
        BDDMockito.given(account.getId()).willReturn(accountId);
        BDDMockito.given(account.getEmail()).willReturn(AN_EMAIL);

        // when
        this.accountRepositoryInMemory.addAccount(account);

        // then
        Account customer = this.accountRepositoryInMemory.findAccountByEmail(AN_EMAIL);
        assertEquals(customer, account);
    }
}
