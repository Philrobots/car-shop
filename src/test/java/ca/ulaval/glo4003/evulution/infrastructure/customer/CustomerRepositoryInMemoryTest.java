package ca.ulaval.glo4003.evulution.infrastructure.customer;

import ca.ulaval.glo4003.evulution.domain.customer.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
public class CustomerRepositoryInMemoryTest {

    private String AN_EMAIL = "tiray@vincent.yuk.com";

    @Mock
    private Customer customer;

    private CustomerRepositoryInMemory customerRepositoryInMemory;

    @BeforeEach
    public void setUp() {
        customerRepositoryInMemory = new CustomerRepositoryInMemory();
        BDDMockito.given(customer.getEmail()).willReturn(AN_EMAIL);
    }

    @Test
    public void givenAnAccount_whenGetAccounts_thenShouldContainsTheAccount() {
        // given
        this.customerRepositoryInMemory.addAccount(customer);

        // when
        List<Customer> customers = this.customerRepositoryInMemory.getAll();

        // then
        assertThat(customers, org.hamcrest.Matchers.hasItem(customer));
    }
}
