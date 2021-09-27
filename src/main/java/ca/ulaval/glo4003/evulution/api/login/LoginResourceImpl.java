package ca.ulaval.glo4003.evulution.api.login;

import ca.ulaval.glo4003.evulution.api.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.api.login.dto.LoginDto;
import ca.ulaval.glo4003.evulution.api.validators.ConstraintsValidator;
import ca.ulaval.glo4003.evulution.domain.exception.GenericException;
import ca.ulaval.glo4003.evulution.service.login.LoginService;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class LoginResourceImpl implements LoginResource {
    private final LoginService loginService;
    private final HTTPExceptionResponseAssembler httpExceptionResponseAssembler;
    private final ConstraintsValidator constraintsValidator;

    public LoginResourceImpl(LoginService loginService, HTTPExceptionResponseAssembler httpExceptionResponseAssembler,
            ConstraintsValidator constraintsValidator) {
        this.loginService = loginService;
        this.httpExceptionResponseAssembler = httpExceptionResponseAssembler;
        this.constraintsValidator = constraintsValidator;
    }

    @Override
    public Response loginCustomer(LoginDto loginDto) {
        try {
            this.constraintsValidator.validate(loginDto);
            TokenDto tokenDto = this.loginService.loginCustomer(loginDto);

            return Response.ok(tokenDto, MediaType.APPLICATION_JSON).status(200, "Login successful").build();
        } catch (GenericException e) {
            return httpExceptionResponseAssembler.assembleResponseFromExceptionClass(e.getClass());
        }
    }
}
