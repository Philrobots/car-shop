package ca.ulaval.glo4003.evulution.infrastructure.account;

import ca.ulaval.glo4003.evulution.domain.account.Account;
import ca.ulaval.glo4003.evulution.domain.account.customer.Customer;
import ca.ulaval.glo4003.evulution.domain.account.manager.Manager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AccountRepositoryInMemoryTest {
    private static final String AN_EMAIL = "tiray@vincent.yuk.com";

    @Mock
    private Customer customer;

    @Mock
    private Manager manager;

    private AccountRepositoryInMemory accountRepositoryInMemory;

    @BeforeEach
    public void setUp() {
        this.accountRepositoryInMemory = new AccountRepositoryInMemory();
    }

    @Test
    public void givenCustomerAccount_whenAddAccount_thenAccountRepositoryInMemoryContainsAccount() {
        // given
        BDDMockito.given(this.customer.getEmail()).willReturn(AN_EMAIL);

        // when
        this.accountRepositoryInMemory.addAccount(this.customer);

        // then
        Account customer = this.accountRepositoryInMemory.getAccountByEmail(AN_EMAIL);
        assertEquals(customer, this.customer);
    }

    @Test
    public void givenManagerAccount_whenAddAccount_thenAccountRepositoryInMemoryContainsAccount() {
        // given
        BDDMockito.given(this.manager.getEmail()).willReturn(AN_EMAIL);

        // when
        this.accountRepositoryInMemory.addAccount(this.manager);

        // then
        Account manager = this.accountRepositoryInMemory.getAccountByEmail(AN_EMAIL);
        assertEquals(manager, this.manager);
    }
}
