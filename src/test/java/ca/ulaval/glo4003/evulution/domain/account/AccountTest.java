package ca.ulaval.glo4003.evulution.domain.account;

import ca.ulaval.glo4003.evulution.domain.account.customer.Customer;
import ca.ulaval.glo4003.evulution.domain.account.customer.Gender;
import ca.ulaval.glo4003.evulution.domain.account.exceptions.FailedLoginException;
import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.domain.token.TokenFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class AccountTest {

    private static final String A_NAME = "name";
    private static final String AN_EMAIL = "email";
    private static final String ANOTHER_EMAIL = "email2";
    private static final String A_PASSWORD = "password";
    private static final String ANOTHER_PASSWORD = "password2";

    private static final LocalDate A_BIRTHDATE = LocalDate.of(1999, 9, 9);

    private static final Gender gender = Gender.MEN;

    @Mock
    private TokenFactory tokenFactory;

    @Mock
    private Token token;

    private Account account;


    @BeforeEach void setUp() {
        account = new Customer(A_NAME, A_BIRTHDATE, AN_EMAIL, A_PASSWORD, gender);
    }

    @Test
    public void givenInvalidEmail_whenLogin_thenFailedLoginException() {
        // when & then
        Assertions.assertThrows(FailedLoginException.class, () -> account.login(ANOTHER_EMAIL, A_PASSWORD, tokenFactory));
    }

    @Test
    public void givenInvalidPassword_whenLogin_thenFailedLoginException() {
        // when & then
        Assertions.assertThrows(FailedLoginException.class, () -> account.login(AN_EMAIL,
            ANOTHER_PASSWORD, tokenFactory));
    }

    @Test
    public void whenLogin_thenTokenFactoryGeneratesNewToken() throws FailedLoginException {
        // given
        BDDMockito.given(tokenFactory.generateNewToken()).willReturn(token);

        // when
        account.login(AN_EMAIL, A_PASSWORD, tokenFactory);

        // then
        Mockito.verify(tokenFactory).generateNewToken();
    }



}
