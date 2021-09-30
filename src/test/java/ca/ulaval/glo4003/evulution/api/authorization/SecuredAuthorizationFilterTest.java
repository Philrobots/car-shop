package ca.ulaval.glo4003.evulution.api.authorization;

import ca.ulaval.glo4003.evulution.api.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDtoAssembler;
import ca.ulaval.glo4003.evulution.domain.token.exception.UnauthorizedRequestException;
import ca.ulaval.glo4003.evulution.service.authorization.AuthorizationService;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class SecuredAuthorizationFilterTest {

    private final String A_HEADER_STRING = "ASDF";

    @Mock
    ContainerRequestContext containerRequestContext;

    @Mock
    HTTPExceptionResponseAssembler httpExceptionResponseAssembler;

    @Mock
    AuthorizationService authorizationService;

    @Mock
    TokenDtoAssembler tokenDtoAssembler;

    @Mock
    TokenDto tokenDto;

    private SecuredAuthorizationFilter securedAuthorizationFilter;

    @BeforeEach
    public void setUp() {
        securedAuthorizationFilter = new SecuredAuthorizationFilter(authorizationService, tokenDtoAssembler,
                httpExceptionResponseAssembler);
    }

    @Test
    public void whenFilter_thenContainerRequestContextGetsHeaderString() throws IOException {
        BDDMockito.given(containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION)).willReturn(A_HEADER_STRING);
        BDDMockito.given(tokenDtoAssembler.assembleFromString(A_HEADER_STRING)).willReturn(tokenDto);

        // when
        securedAuthorizationFilter.filter(containerRequestContext);

        // then
        BDDMockito.verify(authorizationService).validateToken(tokenDto);
    }

    @Test
    public void givenInvalidToken_whenFilter_thenContainerRequestContextGetsHeaderString() throws IOException {
        BDDMockito.given(containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION)).willReturn(A_HEADER_STRING);
        BDDMockito.given(tokenDtoAssembler.assembleFromString(A_HEADER_STRING)).willReturn(tokenDto);
        doThrow(UnauthorizedRequestException.class).when(authorizationService).validateToken(tokenDto);

        // when
        securedAuthorizationFilter.filter(containerRequestContext);

        // then
        BDDMockito.verify(httpExceptionResponseAssembler).assembleResponseFromExceptionClass(UnauthorizedRequestException.class);
    }
}
