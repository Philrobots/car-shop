package ca.ulaval.glo4003.evulution.api.authorization;

import ca.ulaval.glo4003.evulution.api.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDtoAssembler;
import ca.ulaval.glo4003.evulution.api.exceptions.BadInputParameterException;
import ca.ulaval.glo4003.evulution.domain.exception.GenericException;
import ca.ulaval.glo4003.evulution.service.authorization.AuthorizationService;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;

@SecuredWithTransactionId
@Provider
@Priority(Priorities.AUTHORIZATION)
public class SecuredWithTransactionIdAuthorizationFilter implements ContainerRequestFilter {
    private final AuthorizationService authorizationService;
    private final TokenDtoAssembler tokenDtoAssembler;
    private HTTPExceptionResponseAssembler httpExceptionResponseAssembler;

    public SecuredWithTransactionIdAuthorizationFilter(AuthorizationService authorizationService, TokenDtoAssembler tokenDtoAssembler,
                                                       HTTPExceptionResponseAssembler httpExceptionResponseAssembler) {
        this.authorizationService = authorizationService;
        this.tokenDtoAssembler = tokenDtoAssembler;
        this.httpExceptionResponseAssembler = httpExceptionResponseAssembler;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        String authorizationToken = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        String pathParam = containerRequestContext.getUriInfo().getPathParameters().values().iterator().next().get(0);
        TokenDto tokenDto = tokenDtoAssembler.assembleFromString(authorizationToken);

        try {
            validateToken(tokenDto, pathParam);
        } catch (GenericException e) {
            containerRequestContext
                    .abortWith(httpExceptionResponseAssembler.assembleResponseFromExceptionClass(e.getClass()));
        }
    }

    private void validateToken(TokenDto tokenDto, String transactionId) {
        try{
            this.authorizationService.validateTokenWithTransactionId(tokenDto, Integer.parseInt(transactionId));
        } catch (NumberFormatException e){
            throw new BadInputParameterException();
        }
    }
}
