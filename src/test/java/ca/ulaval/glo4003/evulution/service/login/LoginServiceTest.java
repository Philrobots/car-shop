package ca.ulaval.glo4003.evulution.service.login;

import ca.ulaval.glo4003.evulution.domain.account.Account;
import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.account.AccountRepository;
import ca.ulaval.glo4003.evulution.domain.account.exceptions.FailedLoginException;
import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.domain.token.TokenFactory;
import ca.ulaval.glo4003.evulution.domain.token.TokenRepository;
import ca.ulaval.glo4003.evulution.infrastructure.account.exceptions.AccountNotFoundException;
import ca.ulaval.glo4003.evulution.service.authorization.TokenAssembler;
import ca.ulaval.glo4003.evulution.service.login.dto.LoginDto;
import ca.ulaval.glo4003.evulution.service.login.exceptions.ServiceUnableToLoginException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
    private static final String A_STRING_EMAIL = "tiray@expat.com";
    private static final String A_PASSWORD = "qwerty";

    @Mock
    private Token token;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private TokenFactory tokenFactory;

    @Mock
    private TokenAssembler tokenAssembler;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private LoginDto loginDto;

    @Mock
    private Account account;

    @Mock
    private AccountId accountId;

    private LoginService loginService;

    @BeforeEach
    public void setUp() {
        loginDto.email = A_STRING_EMAIL;
        loginDto.password = A_PASSWORD;
        loginService = new LoginService(tokenFactory, tokenRepository, accountRepository, tokenAssembler);
    }

    @Test
    public void whenLoginCustomer_thenAccountRepositoryFindsAccountByEmail() throws AccountNotFoundException {
        // given
        BDDMockito.given(accountRepository.findAccountByEmail(A_STRING_EMAIL)).willReturn(account);

        // when
        this.loginService.loginCustomer(loginDto);

        // then
        Mockito.verify(accountRepository).findAccountByEmail(A_STRING_EMAIL);
    }

    @Test
    public void whenLoginCustomer_thenAccountLogins() throws AccountNotFoundException, FailedLoginException {
        // given
        BDDMockito.given(accountRepository.findAccountByEmail(A_STRING_EMAIL)).willReturn(account);

        // when
        this.loginService.loginCustomer(loginDto);

        // then
        Mockito.verify(account).login(A_STRING_EMAIL, A_PASSWORD, tokenFactory);
    }

    @Test
    public void whenLoginCustomer_thenTokenRepositoryAddsToken() throws AccountNotFoundException, FailedLoginException {
        // given
        BDDMockito.given(accountRepository.findAccountByEmail(A_STRING_EMAIL)).willReturn(account);
        BDDMockito.given(account.login(A_STRING_EMAIL, A_PASSWORD, tokenFactory)).willReturn(token);
        BDDMockito.given(account.getId()).willReturn(accountId);

        // when
        this.loginService.loginCustomer(loginDto);

        // then
        Mockito.verify(tokenRepository).addToken(token, accountId);
    }

    @Test
    public void whenLoginCustomer_thenTokenAssemblerAssemblesDtoFromToken()
            throws AccountNotFoundException, FailedLoginException {
        // given
        BDDMockito.given(accountRepository.findAccountByEmail(A_STRING_EMAIL)).willReturn(account);
        BDDMockito.given(account.login(A_STRING_EMAIL, A_PASSWORD, tokenFactory)).willReturn(token);

        // when
        this.loginService.loginCustomer(loginDto);

        // then
        Mockito.verify(tokenAssembler).assembleDtoFromToken(token);
    }

    @Test
    public void givenInvalidAccount_whenLoginCustomer_thenThrowServiceUnableToLoginException()
            throws AccountNotFoundException {
        // given
        BDDMockito.doThrow(AccountNotFoundException.class).when(accountRepository).findAccountByEmail(A_STRING_EMAIL);

        // then
        Assertions.assertThrows(ServiceUnableToLoginException.class, () -> this.loginService.loginCustomer(loginDto));
    }

    @Test
    public void givenInvalidPassword_whenLoginCustomer_thenThrowServiceUnableToLoginException()
            throws AccountNotFoundException, FailedLoginException {
        // given
        BDDMockito.given(accountRepository.findAccountByEmail(A_STRING_EMAIL)).willReturn(account);
        BDDMockito.doThrow(FailedLoginException.class).when(account).login(A_STRING_EMAIL, A_PASSWORD, tokenFactory);

        // then
        Assertions.assertThrows(ServiceUnableToLoginException.class, () -> this.loginService.loginCustomer(loginDto));
    }
}
