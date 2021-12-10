package ca.ulaval.glo4003.evulution.api.login;

import ca.ulaval.glo4003.evulution.api.login.assembler.LoginDtoAssembler;
import ca.ulaval.glo4003.evulution.api.login.assembler.TokenResponseAssembler;
import ca.ulaval.glo4003.evulution.api.login.dto.LoginRequest;
import ca.ulaval.glo4003.evulution.api.login.dto.TokenResponse;
import ca.ulaval.glo4003.evulution.api.mappers.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.validators.ConstraintsValidator;
import ca.ulaval.glo4003.evulution.service.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.service.login.LoginService;
import ca.ulaval.glo4003.evulution.service.login.dto.LoginDto;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LoginResourceTest {
    private static final int RESPONSE_STATUS_200 = 200;

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
    private TokenDto tokenDto;

    @Mock
    private ConstraintsValidator constraintsValidator;

    @Mock
    private TokenResponse tokenResponse;

    @BeforeEach
    public void setUp() {
        loginResource = new LoginResource(loginService, httpExceptionResponseAssembler, loginDtoAssembler,
                tokenResponseAssembler, constraintsValidator);
    }

    @Test
    public void whenLoginCustomer_thenConstraintValidatorValidates(){
        // when
        loginResource.loginCustomer(loginRequest);

        // then
        Mockito.verify(constraintsValidator).validate(loginRequest);
    }

    @Test
    public void whenLoginCustomer_thenLoginDtoAssemblesFromRequest() {
        // when
        this.loginResource.loginCustomer(loginRequest);

        // then
        Mockito.verify(loginDtoAssembler).fromRequest(loginRequest);
    }


    @Test
    public void whenLoginCustomer_thenLoginServiceLogsInCustomer(){
        // given
        BDDMockito.given(loginDtoAssembler.fromRequest(loginRequest)).willReturn(loginDto);
        BDDMockito.given((loginService.loginCustomer(loginDto))).willReturn(tokenDto);

        // when
        loginResource.loginCustomer(loginRequest);

        // then
        Mockito.verify(loginService).loginCustomer(loginDto);
    }

    @Test
    public void whenLoginCustomer_thenTokenResponseAssemblerAssemblesToResponse(){
        // given
        BDDMockito.given(loginDtoAssembler.fromRequest(loginRequest)).willReturn(loginDto);
        BDDMockito.given(loginService.loginCustomer(loginDto)).willReturn(tokenDto);

        // when
        this.loginResource.loginCustomer(loginRequest);

        // then
        Mockito.verify(tokenResponseAssembler).toResponse(tokenDto);
    }

    @Test
    public void whenLoginCustomer_thenReturns200(){
        // given
        BDDMockito.given(loginDtoAssembler.fromRequest(loginRequest)).willReturn(loginDto);
        BDDMockito.given(loginService.loginCustomer(loginDto)).willReturn(tokenDto);
        BDDMockito.given(tokenResponseAssembler.toResponse(tokenDto)).willReturn(tokenResponse);

        // when
        Response response = this.loginResource.loginCustomer(loginRequest);

        // then
        Assertions.assertEquals(RESPONSE_STATUS_200, response.getStatus());
    }
}

