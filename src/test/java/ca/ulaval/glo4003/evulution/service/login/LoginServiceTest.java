package ca.ulaval.glo4003.evulution.service.login;

import ca.ulaval.glo4003.evulution.domain.account.Account;
import ca.ulaval.glo4003.evulution.domain.account.AccountRepository;
import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.domain.token.TokenFactory;
import ca.ulaval.glo4003.evulution.domain.token.TokenRepository;
import ca.ulaval.glo4003.evulution.service.authorization.TokenAssembler;
import ca.ulaval.glo4003.evulution.service.login.dto.LoginDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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

    private LoginService loginService;

    @BeforeEach
    public void setUp() {
        loginService = new LoginService(tokenFactory, tokenRepository, accountRepository, tokenAssembler);
    } /*
       *
       * @Test public void givenAnEmail_whenLoginCustomer_thenCustomerRepositoryGetsAccountByEmail() throws
       * AccountNotFoundException { // then BDDMockito.given(loginDto.email).willReturn(A_STRING_EMAIL);
       *
       * // when loginService.loginCustomer(loginDto);
       *
       * // then Mockito.verify(accountRepository).getAccountByEmail(A_STRING_EMAIL); }
       *
       * @Test public void givenAnEmail_whenLoginCustomer_thenAccountLogins() throws FailedLoginException { // then
       * BDDMockito.given(loginDto.email).willReturn(A_STRING_EMAIL);
       * BDDMockito.given(loginDto.password).willReturn(A_PASSWORD);
       *
       * // when loginService.loginCustomer(loginDto);
       *
       * // then Mockito.verify(account).login(A_STRING_EMAIL, A_PASSWORD, tokenFactory, tokenRepository); }
       *
       * @Test public void givenAnEmail_whenLoginCustomer_thenTokenAssembleAssembleDto() throws FailedLoginException {
       * // then BDDMockito.given(loginDto.email).willReturn(A_STRING_EMAIL);
       * BDDMockito.given(loginDto.password).willReturn(A_PASSWORD); BDDMockito.given(account.login(A_STRING_EMAIL,
       * A_PASSWORD, tokenFactory, tokenRepository)).willReturn(token);
       *
       * // when loginService.loginCustomer(loginDto);
       *
       * // then Mockito.verify(tokenAssembler).assembleDtoFromToken(token); }
       */
}
