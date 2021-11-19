package ca.ulaval.glo4003.evulution.api.authorization;

import ca.ulaval.glo4003.evulution.api.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDtoAssembler;
import ca.ulaval.glo4003.evulution.domain.exceptions.GenericException;
import ca.ulaval.glo4003.evulution.service.authorization.AuthorizationService;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.ext.Provider;

@SecuredAdmin
@Provider
@Priority(Priorities.AUTHORIZATION)
public class SecuredAdminAuthorizationFilter implements ContainerRequestFilter {
    private final AuthorizationService authorizationService;
    private final TokenDtoAssembler tokenDtoAssembler;
    private final HTTPExceptionResponseAssembler httpExceptionResponseAssembler;

    public SecuredAdminAuthorizationFilter(AuthorizationService authorizationService,
            TokenDtoAssembler tokenDtoAssembler, HTTPExceptionResponseAssembler httpExceptionResponseAssembler) {
        this.authorizationService = authorizationService;
        this.tokenDtoAssembler = tokenDtoAssembler;
        this.httpExceptionResponseAssembler = httpExceptionResponseAssembler;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        String authorizationToken = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        TokenDto tokenDto = tokenDtoAssembler.assembleFromString(authorizationToken);

        try {
            this.authorizationService.validateAdminToken(tokenDto);
        } catch (GenericException e) {
            containerRequestContext
                    .abortWith(httpExceptionResponseAssembler.assembleResponseFromExceptionClass(e.getClass()));
        }
    }
}
