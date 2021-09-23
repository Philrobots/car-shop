package ca.ulaval.glo4003.evulution.api.authorization;

import ca.ulaval.glo4003.evulution.api.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDtoAssembler;
import ca.ulaval.glo4003.evulution.domain.exception.GenericException;
import ca.ulaval.glo4003.evulution.service.authorization.AuthorizationService;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Secured
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {
    private final AuthorizationService authorizationService;
    private final TokenDtoAssembler tokenDtoAssembler;
    private HTTPExceptionResponseAssembler httpExceptionResponseAssembler;

    public AuthorizationFilter(AuthorizationService authorizationService, TokenDtoAssembler tokenDtoAssembler, HTTPExceptionResponseAssembler httpExceptionResponseAssembler) {
        this.authorizationService = authorizationService;
        this.tokenDtoAssembler = tokenDtoAssembler;
        this.httpExceptionResponseAssembler = httpExceptionResponseAssembler;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String authorizationToken = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        TokenDto tokenDto = tokenDtoAssembler.assembleFromString(authorizationToken);

        try {
            validateToken(tokenDto);
        } catch (GenericException e) {
            containerRequestContext.abortWith(httpExceptionResponseAssembler.assembleResponseFromExceptionClass(e.getClass()));
        }
    }

    private void validateToken(TokenDto tokenDto) {
        this.authorizationService.ValidateToken(tokenDto);
    }
}
