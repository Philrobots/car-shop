package ca.ulaval.glo4003.evulution.api.login;

import ca.ulaval.glo4003.evulution.api.login.assembler.LoginDtoAssembler;
import ca.ulaval.glo4003.evulution.api.login.assembler.TokenResponseAssembler;
import ca.ulaval.glo4003.evulution.api.login.dto.LoginRequest;
import ca.ulaval.glo4003.evulution.api.mappers.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.validators.ConstraintsValidator;
import ca.ulaval.glo4003.evulution.service.login.LoginService;
import ca.ulaval.glo4003.evulution.service.login.dto.LoginDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LoginResourceTest {
    private static final Integer BAD_INPUT_PARAMETERS_ERROR_CODE = 400;
    private static final String BAD_INPUT_PARAMETERS_ERROR_MESSAGE = "bad input parameter";

    private LoginResource loginResource;

    @Mock
    private LoginService loginService;

    @Mock
    private HTTPExceptionResponseAssembler httpExceptionResponseAssembler;

    @Mock
    private LoginDtoAssembler loginDtoAssembler;

    @Mock
    private TokenResponseAssembler tokenResponseAssembler;

    @Mock
    private LoginRequest loginRequest;

    @Mock
    private LoginDto loginDto;

    @Mock
    private ConstraintsValidator constraintsValidator;

    @BeforeEach
    public void setUp() {
        loginResource = new LoginResource(loginService, httpExceptionResponseAssembler, loginDtoAssembler,
                tokenResponseAssembler, constraintsValidator);
    }
    /*
     * @Test public void givenInvalidConstraints_whenChooseBattery_thenReturnsAccordingErrorResponse() {
     *
     * Mockito.doThrow(BadInputParameterException.class).when(constraintsValidator).validate(loginRequest);
     *
     * // when Response response = loginResource.loginCustomer(loginRequest);
     *
     * // then assertEquals(response.getStatus(), BAD_INPUT_PARAMETERS_ERROR_CODE); assertEquals(response.getEntity(),
     * BAD_INPUT_PARAMETERS_ERROR_MESSAGE); }
     *
     * @Test public void givenLoginDto_whenLoginCustomer_thenLoginServiceCallsLoginCustomer() { // when
     * loginResource.loginCustomer(loginRequest);
     *
     * // then Mockito.verify(loginService).loginCustomer(loginDto); }
     */

}
