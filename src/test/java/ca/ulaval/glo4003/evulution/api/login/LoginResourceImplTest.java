package ca.ulaval.glo4003.evulution.api.login;

import ca.ulaval.glo4003.evulution.api.login.dto.LoginDto;
import ca.ulaval.glo4003.evulution.domain.login.NoAccountFoundException;
import ca.ulaval.glo4003.evulution.service.login.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LoginResourceImplTest {

    @Mock
    private LoginService loginService;

    @Mock
    private LoginDto loginDto;

    private LoginResource loginResource;

    @BeforeEach
    public void setUp() {
        loginResource = new LoginResourceImpl(loginService);
    }

    @Test
    public void givenLoginDto_whenLoginCustomer_thenLoginServiceCallsLoginCustomer() throws NoAccountFoundException {
        // when
        loginResource.loginCustomer(loginDto);

        // then
        Mockito.verify(loginService).loginCustomer(loginDto);
    }

}
