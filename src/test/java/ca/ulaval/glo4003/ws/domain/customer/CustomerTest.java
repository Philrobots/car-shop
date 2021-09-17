package ca.ulaval.glo4003.ws.domain.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CustomerTest {

    private String AN_EMAIL = "kevin@expat.com";
    private String A_PASSWORD = "Fireball";
    private String A_NAME = "JaggerBom";
    private Date birthdate = new Date();

    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer(A_NAME, birthdate, AN_EMAIL, A_PASSWORD);
    }

    @Test
    public void givenAnRandomPassword_whenVerifyIfAuthIsCorrect_thenShouldNotBeCorrect() {
        // given
        String badPassword = "12FireBallSvp";

        // when
        boolean isAuthCorrect = customer.isRightCustomerInfo(AN_EMAIL, badPassword);

        // given
        assertFalse(isAuthCorrect);
    }

    @Test
    public void givenAGoodPasswordAndGoodEmail_whenVerifyIfAuthIsCorrect_thenShouldBeCorrect() {
        // when
        boolean isAuthCorrect = customer.isRightCustomerInfo(AN_EMAIL, A_PASSWORD);

        // given
        assertTrue(isAuthCorrect);
    }

}
