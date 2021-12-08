package ca.ulaval.glo4003.evulution.api.authorization;

import ca.ulaval.glo4003.evulution.api.authorization.assemblers.TokenDtoAssembler;
import ca.ulaval.glo4003.evulution.api.mappers.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.service.authorization.AuthorizationService;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SecuredAdminAuthorizationFilterTest {

    private SecuredAdminAuthorizationFilter securedAdminAuthorizationFilter;

    @Mock
    private AuthorizationService authorizationService;

    @Mock
    private TokenDtoAssembler tokenDtoAssembler;

    @Mock
    private HTTPExceptionResponseAssembler httpExceptionResponseAssembler;

    @Mock
    private ContainerRequestContext containerRequestContext;

    @BeforeEach
    void setUp() {
        this.securedAdminAuthorizationFilter = new SecuredAdminAuthorizationFilter(authorizationService,
                tokenDtoAssembler, httpExceptionResponseAssembler);
    }

    @Test
    public void whenFilter_thenContainerRequestContextGetsAuthorizationHeader() {
        // when
        this.securedAdminAuthorizationFilter.filter(containerRequestContext);

        // then
        Mockito.verify(containerRequestContext).getHeaderString(HttpHeaders.AUTHORIZATION);
    }
}
