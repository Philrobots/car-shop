package ca.ulaval.glo4003.evulution.api.authorization;

import ca.ulaval.glo4003.evulution.api.authorization.assemblers.TokenDtoAssembler;
import ca.ulaval.glo4003.evulution.api.mappers.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.service.authorization.AuthorizationService;
import ca.ulaval.glo4003.evulution.service.authorization.dto.TokenDto;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    // @Test
    // public void whenFilter_thenContainerRequestContextGetsHeaderString() {
    // BDDMockito.given(containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION))
    // .willReturn(A_HEADER_STRING);
    // BDDMockito.given(tokenDtoAssembler.assembleFromString(A_HEADER_STRING)).willReturn(tokenDto);
    //
    // // when
    // securedAuthorizationFilter.filter(containerRequestContext);
    //
    // // then
    // BDDMockito.verify(authorizationService).getAccountId(tokenDto);
    // }
    //
    // @Test
    // public void givenInvalidToken_whenFilter_thenContainerRequestContextGetsHeaderString() {
    // BDDMockito.given(containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION))
    // .willReturn(A_HEADER_STRING);
    // BDDMockito.given(tokenDtoAssembler.assembleFromString(A_HEADER_STRING)).willReturn(tokenDto);
    // doThrow(UnauthorizedRequestException.class).when(authorizationService).getAccountId(tokenDto);
    //
    // // when
    // securedAuthorizationFilter.filter(containerRequestContext);
    //
    // // then
    // BDDMockito.verify(httpExceptionResponseAssembler)
    // .assembleResponseFromExceptionClass(UnauthorizedRequestException.class);
    // }
}
