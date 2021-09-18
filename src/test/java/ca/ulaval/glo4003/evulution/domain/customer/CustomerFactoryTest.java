package ca.ulaval.glo4003.evulution.domain.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CustomerFactoryTest {

    private String AN_EMAIl = "tiray@expat.com";
    private String A_PASSWORD = "123456";
    private Date BIRTH_DATE = new Date();
    private String A_NAME = "TI RAY EXPAT";

    private CustomerFactory customerFactory;

    @BeforeEach
    public void setUp() {
        customerFactory = new CustomerFactory();
    }

    @Test
    public void givenInformation_whenCreateCustomer_thenCustomerShouldHaveTheSameInformation() {
        // when
        Customer customer = customerFactory.create(A_NAME, BIRTH_DATE, AN_EMAIl, A_PASSWORD);

        // then
        assertEquals(customer.getEmail(), AN_EMAIl);
        assertEquals(customer.getBirthdate(), BIRTH_DATE);
        assertEquals(customer.getName(), A_NAME);
        assertEquals(customer.getPassword(), A_PASSWORD);
    }
}
