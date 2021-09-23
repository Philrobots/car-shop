package ca.ulaval.glo4003.evulution.api.authorization;

import ca.ulaval.glo4003.evulution.api.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDtoAssembler;
import ca.ulaval.glo4003.evulution.service.authorization.AuthorizationService;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class AuthorizationFilterTest {

    @Mock
    ContainerRequestContext containerRequestContext;

    @Mock
    HTTPExceptionResponseAssembler httpExceptionResponseAssembler;

    @Mock
    AuthorizationService authorizationService;

    @Mock
    TokenDtoAssembler tokenDtoAssembler;

    private AuthorizationFilter authorizationFilter;

    @BeforeEach
    public void setUp() {
        authorizationFilter = new AuthorizationFilter(authorizationService, tokenDtoAssembler, httpExceptionResponseAssembler);
    }

    @Test
    public void whenFilter_thenContainerRequestContextGetsHeaderString() throws IOException {

        // when
        authorizationFilter.filter(containerRequestContext);

        // then
        // Mockito.verify(containerRequestContext).getHeaderString(HttpHeaders.AUTHORIZATION);

    }
}
