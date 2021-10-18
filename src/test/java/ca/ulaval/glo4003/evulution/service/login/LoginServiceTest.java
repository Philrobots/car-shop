package ca.ulaval.glo4003.evulution.service.login;

import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.api.login.dto.LoginDto;
import ca.ulaval.glo4003.evulution.domain.account.AccountRepository;
import ca.ulaval.glo4003.evulution.domain.customer.Customer;
import ca.ulaval.glo4003.evulution.domain.customer.CustomerRepository;
import ca.ulaval.glo4003.evulution.domain.login.LoginValidator;
import ca.ulaval.glo4003.evulution.domain.login.exception.NoAccountFoundException;
import ca.ulaval.glo4003.evulution.domain.token.Token;
import ca.ulaval.glo4003.evulution.domain.token.TokenFactory;
import ca.ulaval.glo4003.evulution.service.authorization.TokenAssembler;
import ca.ulaval.glo4003.evulution.service.authorization.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
    private static final String A_STRING_EMAIL = "tiray@expat.com";

    @Mock
    private Token token;

    @Mock
    private TokenDto tokenDto;

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
    private LoginValidator loginValidator;

    @Mock
    private Customer customer;

    private LoginService loginService;

    @BeforeEach
    public void setUp() {
        loginDto.email = A_STRING_EMAIL;
        BDDMockito.given(accountRepository.getAccountByEmail(A_STRING_EMAIL)).willReturn(customer);
        BDDMockito.given(tokenAssembler.tokenToDto(token)).willReturn(tokenDto);
        BDDMockito.given(tokenFactory.generateNewToken()).willReturn(token);
        loginService = new LoginService(tokenFactory, tokenRepository, tokenAssembler, accountRepository,
                loginValidator);
    }

    @Test
    public void givenAnEmail_whenLoginCustomer_thenCustomerRepositoryGetsAccountByEmail()
            throws NoAccountFoundException {
        // when
        loginService.loginCustomer(loginDto);

        // then
        Mockito.verify(accountRepository).getAccountByEmail(A_STRING_EMAIL);
    }

    @Test
    public void givenAnEmail_whenLoginCustomer_thenLoginValidationValidatesLogin() throws NoAccountFoundException {
        // when
        loginService.loginCustomer(loginDto);

        // then
        Mockito.verify(loginValidator).validateLogin(customer, loginDto);
    }

    @Test
    public void givenAnEmail_whenLoginCustomer_thenTokenRepositoryRegistersActiveEmail()
            throws NoAccountFoundException {
        // when
        loginService.loginCustomer(loginDto);

        // then
        Mockito.verify(tokenRepository).addTokenWithEmail(token, A_STRING_EMAIL);
    }

    @Test
    public void givenAnEmail_whenLoginCustomer_thenTokenAssemblerAssemblesTokenToDto() throws NoAccountFoundException {
        // when
        loginService.loginCustomer(loginDto);

        // then
        Mockito.verify(tokenAssembler).tokenToDto(token);
    }

    @Test
    public void givenAnEmail_whenLoginCustomer_thenReturnsTheRightDto() throws NoAccountFoundException {

        // when
        TokenDto actualTokenDto = loginService.loginCustomer(loginDto);

        // then
        assertEquals(tokenDto, actualTokenDto);
    }

}
