package ca.ulaval.glo4003.evulution.api.authorization;

import ca.ulaval.glo4003.evulution.api.authorization.assemblers.TokenDtoAssembler;
import ca.ulaval.glo4003.evulution.api.mappers.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.service.authorization.AuthorizationService;
import ca.ulaval.glo4003.evulution.service.authorization.dto.TokenDto;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SecuredAuthorizationFilterTest {
    private final static String AN_AUTHORIZATION_TOKEN = "auth";

    @Mock
    private AuthorizationService authorizationService;

    @Mock
    private TokenDtoAssembler tokenDtoAssembler;

    @Mock
    private HTTPExceptionResponseAssembler httpExceptionResponseAssembler;

    @Mock
    private ContainerRequestContext containerRequestContext;

    @Mock
    private TokenDto tokenDto;

    private SecuredAuthorizationFilter securedAuthorizationFilter;

    @BeforeEach
    public void setUp() {
        securedAuthorizationFilter = new SecuredAuthorizationFilter(authorizationService, tokenDtoAssembler,
                httpExceptionResponseAssembler);
    }

    @Test
    public void whenFilter_thenContainerRequestContextGetsHeaderString() {
        // when
        securedAuthorizationFilter.filter(containerRequestContext);

        // then
        Mockito.verify(containerRequestContext).getHeaderString(HttpHeaders.AUTHORIZATION);
    }

    @Test
    public void whenFilter_thenTokenDtoAssemblerAssemblesFromString() {
        // given
        BDDMockito.given(containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION))
                .willReturn(AN_AUTHORIZATION_TOKEN);

        // when
        securedAuthorizationFilter.filter(containerRequestContext);

        // then
        Mockito.verify(tokenDtoAssembler).assembleFromString(AN_AUTHORIZATION_TOKEN);
    }

    @Test
    public void whenFilter_thenAuthorizationServiceValidates() {
        // given
        BDDMockito.given(containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION))
                .willReturn(AN_AUTHORIZATION_TOKEN);
        BDDMockito.given(tokenDtoAssembler.assembleFromString(AN_AUTHORIZATION_TOKEN)).willReturn(tokenDto);

        // when
        securedAuthorizationFilter.filter(containerRequestContext);

        // then
        Mockito.verify(authorizationService).validateToken(tokenDto);
    }

}
