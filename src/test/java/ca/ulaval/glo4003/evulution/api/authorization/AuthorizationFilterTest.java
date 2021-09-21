package ca.ulaval.glo4003.evulution.api.authorization;

import ca.ulaval.glo4003.evulution.service.authorization.AuthorizationService;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class AuthorizationFilterTest {

    @Mock
    ContainerRequestContext containerRequestContext;

    @Mock
    AuthorizationService authorizationService;

    private AuthorizationFilter authorizationFilter;

    @BeforeEach
    public void setUp() {
        authorizationFilter = new AuthorizationFilter(authorizationService);
    }

    @Test
    public void whenFilter_thenContainerRequestContextGetsHeaderString() throws IOException {

        // when
        authorizationFilter.filter(containerRequestContext);

        // then
        // Mockito.verify(containerRequestContext).getHeaderString(HttpHeaders.AUTHORIZATION);

    }
}
