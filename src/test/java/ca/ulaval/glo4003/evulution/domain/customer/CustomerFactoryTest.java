package ca.ulaval.glo4003.evulution.domain.customer;

import ca.ulaval.glo4003.evulution.api.exceptions.BadInputParameterException;
import ca.ulaval.glo4003.evulution.api.exceptions.InvalidDateFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CustomerFactoryTest {
    private static final String AN_EMAIl = "tiray@expat.com";
    private static final String A_PASSWORD = "123456";
    private static final String A_BIRTH_DATE = "1999-08-08";
    private static final String A_NAME = "TI RAY EXPAT";
    private static final String A_VALID_GENDER = "W";
    private static final String AN_INVALID_GENDER = "Y";
    private static final String AN_INVALID_DATE = "2070-03-03";

    private CustomerFactory customerFactory;

    @BeforeEach
    public void setUp() {
        customerFactory = new CustomerFactory();
    }

    @Test
    public void whenCreateCustomer_thenCreatesCustomer() {
        // when
        Customer customer = customerFactory.create(A_NAME, A_BIRTH_DATE, AN_EMAIl, A_PASSWORD, A_VALID_GENDER);

        // then
        assertNotNull(customer);
    }

    @Test
    public void givenInvalidDate_whenCreateCustomer_thenThrowsInvalidDateFormatException() {
        assertThrows(InvalidDateFormatException.class,
                () -> customerFactory.create(A_NAME, AN_INVALID_DATE, AN_EMAIl, A_PASSWORD, A_VALID_GENDER));
    }

    @Test
    public void givenInvalidGender_whenCreateCustomer_thenThrowsBadInputParameterException() {
        assertThrows(BadInputParameterException.class,
                () -> customerFactory.create(A_NAME, A_BIRTH_DATE, AN_EMAIl, A_PASSWORD, AN_INVALID_GENDER));
    }

}
