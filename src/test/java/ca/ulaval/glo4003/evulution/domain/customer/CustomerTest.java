package ca.ulaval.glo4003.evulution.domain.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class CustomerTest {

    private static final String AN_EMAIL = "kevin@expat.com";
    private static final String A_PASSWORD = "Fireball";
    private static final String A_NAME = "JaggerBom";
    private static final Date A_BIRTH_DATE = new Date();

    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer(A_NAME, A_BIRTH_DATE, AN_EMAIL, A_PASSWORD);
    }

    @Test
    public void givenAnInvalidPassword_whenIsAuthenticationValid_thenIsInvalid() {
        // given
        String invalidPassword = "12FireBallSvp";

        // when
        boolean isAuthenticationValid = customer.isAuthenticationValid(AN_EMAIL, invalidPassword);

        // given
        assertFalse(isAuthenticationValid);
    }

    @Test
    public void givenValidAuthenticationInfos_whenIsAuthenticationValid_thenIsValid() {
        // when
        boolean isAuthenticationValid = customer.isAuthenticationValid(AN_EMAIL, A_PASSWORD);

        // given
        assertTrue(isAuthenticationValid);
    }

}
