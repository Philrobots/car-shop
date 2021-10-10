package ca.ulaval.glo4003.evulution.infrastructure.customer;

import ca.ulaval.glo4003.evulution.domain.customer.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CustomerRepositoryInMemoryTest {
    private static final String AN_EMAIL = "tiray@vincent.yuk.com";

    @Mock
    private Customer customer;

    private CustomerRepositoryInMemory customerRepositoryInMemory;

    @BeforeEach
    public void setUp() {
        customerRepositoryInMemory = new CustomerRepositoryInMemory();
    }

    @Test
    public void whenAddAccount_thenRepositoryInMemoryContainsAccount() {
        // given
        BDDMockito.given(this.customer.getEmail()).willReturn(AN_EMAIL);

        // when
        this.customerRepositoryInMemory.addCustomer(this.customer);

        // then
        Customer customer = this.customerRepositoryInMemory.getCustomerByEmail(AN_EMAIL);
        assertEquals(customer, this.customer);
    }
}
