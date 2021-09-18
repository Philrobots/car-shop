package ca.ulaval.glo4003.evulution.api.login;

import ca.ulaval.glo4003.evulution.api.login.dto.LoginDto;
import ca.ulaval.glo4003.evulution.domain.login.NoAccountFoundException;
import ca.ulaval.glo4003.evulution.service.login.LoginService;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class LoginResourceImpl implements LoginResource {

    private LoginService loginService;

    public LoginResourceImpl(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public Response loginCustomer(LoginDto loginDto) {
        try {
            return Response.ok(this.loginService.loginCustomer(loginDto), MediaType.APPLICATION_JSON)
                    .status(200, "Login successful").build();
        } catch (NoAccountFoundException e) {
            return Response.status(e.getStatusCode(), e.getMessage()).entity(e.getMessage()).build();
        }
    }
}
