package ca.ulaval.glo4003.evulution.domain.login;

import ca.ulaval.glo4003.evulution.api.login.dto.LoginDto;
import ca.ulaval.glo4003.evulution.domain.account.Account;
import ca.ulaval.glo4003.evulution.domain.login.exceptions.NoAccountFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class LoginValidatorTest {

    private LoginValidator loginValidator;

    @Mock
    private Account account;

    @Mock
    private LoginDto loginDto;

    @BeforeEach
    public void setUp() {
        this.loginValidator = new LoginValidator();
    }

    @Test
    public void givenNoAccount_whenValidateLogin_thenThrowsNoAccountFoundException() {
        // given
        account = null;

        // when
        Executable validateLogin = () -> loginValidator.validateLogin(account, loginDto);

        // then
        assertThrows(NoAccountFoundException.class, validateLogin);
    }

    @Test
    public void givenInvalidAuthentication_whenValidateLogin_thenThrowsNoAccountFoundException() {
        // given
        BDDMockito.given(account.isAuthenticationValid(loginDto.email, loginDto.password)).willReturn(false);

        // when
        Executable validateLogin = () -> loginValidator.validateLogin(account, loginDto);

        // then
        assertThrows(NoAccountFoundException.class, validateLogin);
    }

    @Test
    public void givenValidAuthentication_whenValidateLogin_thenDoesNotThrow() {
        // given
        BDDMockito.given(account.isAuthenticationValid(loginDto.email, loginDto.password)).willReturn(true);

        // when
        Executable validateLogin = () -> loginValidator.validateLogin(account, loginDto);

        // then
        assertDoesNotThrow(validateLogin);
    }
}
