package ca.ulaval.glo4003.evulution.api.login;

import ca.ulaval.glo4003.evulution.api.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.exceptions.BadInputParameterException;
import ca.ulaval.glo4003.evulution.api.login.dto.LoginDto;
import ca.ulaval.glo4003.evulution.api.mappers.HTTPExceptionMapper;
import ca.ulaval.glo4003.evulution.api.validators.ConstraintsValidator;
import ca.ulaval.glo4003.evulution.domain.login.exception.NoAccountFoundException;
import ca.ulaval.glo4003.evulution.service.login.LoginService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class LoginResourceImplTest {
    private static final Integer BAD_INPUT_PARAMETERS_ERROR_CODE = 400;
    private static final String BAD_INPUT_PARAMETERS_ERROR_MESSAGE = "bad input parameter";
    private LoginResource loginResource;
    private HTTPExceptionResponseAssembler httpExceptionResponseAssembler;

    @Mock
    private LoginService loginService;

    @Mock
    private LoginDto loginDto;

    @Mock
    private ConstraintsValidator constraintsValidator;

    @BeforeEach
    public void setUp() {
        httpExceptionResponseAssembler = new HTTPExceptionResponseAssembler(new HTTPExceptionMapper());
        loginResource = new LoginResourceImpl(loginService, httpExceptionResponseAssembler, constraintsValidator);
    }

    @Test
    public void givenInvalidConstraints_whenChooseBattery_thenReturnsAccordingErrorResponse(){

        Mockito.doThrow(BadInputParameterException.class).when(constraintsValidator).validate(loginDto);

        // when
        Response response = loginResource.loginCustomer(loginDto);

        // then
        assertEquals(response.getStatus(), BAD_INPUT_PARAMETERS_ERROR_CODE);
        assertEquals(response.getEntity(), BAD_INPUT_PARAMETERS_ERROR_MESSAGE);
    }

    @Test
    public void givenLoginDto_whenLoginCustomer_thenLoginServiceCallsLoginCustomer() throws NoAccountFoundException {
        // when
        loginResource.loginCustomer(loginDto);

        // then
        Mockito.verify(loginService).loginCustomer(loginDto);
    }

}
