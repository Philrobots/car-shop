package ca.ulaval.glo4003.evulution.api.authorization;

import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.exception.GenericException;
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
    private AuthorizationService authorizationService;

    public AuthorizationFilter(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {

        String authorizationToken = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        try {
            validateToken(authorizationToken);
        } catch (GenericException e) {
            containerRequestContext.abortWith(
                    Response.status(e.getErrorCode(), e.getErrorMessage()).entity(e.getErrorMessage()).build());
        }
    }

    private void validateToken(String token) {
        TokenDto tokenDto = new TokenDto(token);
        this.authorizationService.ValidateToken(tokenDto);
    }
}
