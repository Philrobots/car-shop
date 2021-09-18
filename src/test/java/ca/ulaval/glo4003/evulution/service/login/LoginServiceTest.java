package ca.ulaval.glo4003.evulution.service.login;

import ca.ulaval.glo4003.evulution.api.customer.dto.TokenDto;
import ca.ulaval.glo4003.evulution.api.login.dto.LoginDto;
import ca.ulaval.glo4003.evulution.domain.customer.Customer;
import ca.ulaval.glo4003.evulution.domain.customer.CustomerRepository;
import ca.ulaval.glo4003.evulution.domain.login.LoginValidator;
import ca.ulaval.glo4003.evulution.domain.login.NoAccountFoundException;
import ca.ulaval.glo4003.evulution.domain.token.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    private String AN_EMAIl = "tiray@expat.com";

    @Mock
    private Token token;

    @Mock
    private TokenDto tokenDto;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private TokenAssembler tokenAssembler;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private LoginDto loginDto;

    @Mock
    private LoginValidator loginValidator;

    @Mock
    private Customer customer;

    private LoginService loginService;

    @BeforeEach
    public void setUp() {
        loginDto.email = AN_EMAIl;
        BDDMockito.given(customerRepository.getAccountByEmail(AN_EMAIl)).willReturn(customer);
        BDDMockito.given(tokenRepository.loginCustomer(AN_EMAIl)).willReturn(token);
        BDDMockito.given(tokenAssembler.toDto(token)).willReturn(tokenDto);
        loginService = new LoginService(tokenRepository, tokenAssembler, customerRepository, loginValidator);
    }

    @Test
    public void givenAnEmail_whenLoginCustomer_thenShouldCallTheRepositoryToGetCustomerInformation()
            throws NoAccountFoundException {
        // when
        loginService.loginCustomer(loginDto);

        // then
        Mockito.verify(customerRepository).getAccountByEmail(AN_EMAIl);
    }

    @Test
    public void givenAnEmail_whenLoginCustomer_thenShouldCallTheLoginValidatorToValidateAuthentication()
            throws NoAccountFoundException {
        // when
        loginService.loginCustomer(loginDto);

        // then
        Mockito.verify(loginValidator).validateLogin(customer, loginDto);
    }

    @Test
    public void givenAnEmail_whenLoginCustomer_thenShouldCallTheRepositoryToHandlerToken()
            throws NoAccountFoundException {
        // when
        loginService.loginCustomer(loginDto);

        // then
        Mockito.verify(tokenRepository).loginCustomer(AN_EMAIl);
    }

    @Test
    public void givenAnEmail_whenLoginCustomer_thenShouldCallTheAssemblerToGetTokenDto()
            throws NoAccountFoundException {
        // when
        loginService.loginCustomer(loginDto);

        // then
        Mockito.verify(tokenAssembler).toDto(token);
    }

    @Test
    public void givenAnEmail_whenLoginCustomer_thenShouldReturnTheRightTokenDto() throws NoAccountFoundException {
        // when
        TokenDto actualTokenDto = loginService.loginCustomer(loginDto);

        // then
        assertTrue(tokenDto.equals(actualTokenDto));
    }

}
