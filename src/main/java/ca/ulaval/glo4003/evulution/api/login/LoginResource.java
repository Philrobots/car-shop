package ca.ulaval.glo4003.evulution.api.login;

import ca.ulaval.glo4003.evulution.api.login.assembler.LoginDtoAssembler;
import ca.ulaval.glo4003.evulution.api.login.assembler.TokenResponseAssembler;
import ca.ulaval.glo4003.evulution.api.login.dto.LoginRequest;
import ca.ulaval.glo4003.evulution.api.login.dto.TokenResponse;
import ca.ulaval.glo4003.evulution.api.mappers.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.validators.ConstraintsValidator;
import ca.ulaval.glo4003.evulution.service.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.service.exceptions.GenericException;
import ca.ulaval.glo4003.evulution.service.login.LoginService;
import ca.ulaval.glo4003.evulution.service.login.dto.LoginDto;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/login")
public class LoginResource {
    private final LoginService loginService;
    private final HTTPExceptionResponseAssembler httpExceptionResponseAssembler;
    private final LoginDtoAssembler loginDtoAssembler;
    private final TokenResponseAssembler tokenResponseAssembler;
    private final ConstraintsValidator constraintsValidator;

    public LoginResource(LoginService loginService, HTTPExceptionResponseAssembler httpExceptionResponseAssembler,
                         LoginDtoAssembler loginDtoAssembler, TokenResponseAssembler tokenResponseAssembler,
                         ConstraintsValidator constraintsValidator) {
        this.loginService = loginService;
        this.httpExceptionResponseAssembler = httpExceptionResponseAssembler;
        this.loginDtoAssembler = loginDtoAssembler;
        this.tokenResponseAssembler = tokenResponseAssembler;
        this.constraintsValidator = constraintsValidator;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginCustomer(LoginRequest loginRequest) {
        try {
            this.constraintsValidator.validate(loginRequest);

            LoginDto loginDto = this.loginDtoAssembler.fromRequest(loginRequest);
            TokenDto tokenDto = this.loginService.loginCustomer(loginDto);
            TokenResponse tokenResponse = this.tokenResponseAssembler.toResponse(tokenDto);

            return Response.ok(tokenResponse, MediaType.APPLICATION_JSON).status(200, "Login successful").build();
        } catch (GenericException e) {
            return httpExceptionResponseAssembler.assembleResponseFromExceptionClass(e.getClass());
        }
    }
}
