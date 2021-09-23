package ca.ulaval.glo4003.evulution.api.login;

import ca.ulaval.glo4003.evulution.api.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.login.dto.LoginDto;
import ca.ulaval.glo4003.evulution.domain.exception.GenericException;
import ca.ulaval.glo4003.evulution.domain.login.exception.NoAccountFoundException;
import ca.ulaval.glo4003.evulution.service.login.LoginService;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class LoginResourceImpl implements LoginResource {

    private LoginService loginService;
    private HTTPExceptionResponseAssembler httpExceptionResponseAssembler;

    public LoginResourceImpl(LoginService loginService, HTTPExceptionResponseAssembler httpExceptionResponseAssembler) {
        this.loginService = loginService;
        this.httpExceptionResponseAssembler = httpExceptionResponseAssembler;
    }

    @Override
    public Response loginCustomer(LoginDto loginDto) {
        try {
            return Response.ok(this.loginService.loginCustomer(loginDto), MediaType.APPLICATION_JSON)
                    .status(200, "Login successful").build();
        } catch (GenericException e) {
            return httpExceptionResponseAssembler.assembleResponseFromExceptionClass(e.getClass());
        }
    }
}
