package ca.ulaval.glo4003.evulution.api.authorization;

import ca.ulaval.glo4003.evulution.api.authorization.assemblers.TokenDtoAssembler;
import ca.ulaval.glo4003.evulution.api.mappers.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.service.authorization.AuthorizationService;
import ca.ulaval.glo4003.evulution.service.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadInputParameterException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.UriInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class SecuredWithDeliveryIdAuthorizationFilterTest {

    private final String A_HEADER_STRING = "ASDF";
    private final String A_VALID_PATH_PARAM = "1";
    private final int A_VALID_DELIVERY_ID = 1;
    private final String AN_INVALID_PATH_PARAM = "invalid";

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

    @Mock
    UriInfo uriInfo;

    SecuredWithDeliveryIdAuthorizationFilter securedWithDeliveryIdAuthorizationFilter;

    @BeforeEach
    void setUp() {
        securedWithDeliveryIdAuthorizationFilter = new SecuredWithDeliveryIdAuthorizationFilter(authorizationService,
                tokenDtoAssembler, httpExceptionResponseAssembler);
    }

    @Test
    public void whenFilter_thenAuthorizationServiceIsCalled() {
        BDDMockito.given(containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION))
                .willReturn(A_HEADER_STRING);
        BDDMockito.given(containerRequestContext.getUriInfo()).willReturn(uriInfo);
        BDDMockito.given(tokenDtoAssembler.assembleFromString(A_HEADER_STRING)).willReturn(tokenDto);
        BDDMockito.given(uriInfo.getPathParameters()).willReturn(new MultivaluedHashMap<String, String>() {
            {
                put("", Arrays.asList(A_VALID_PATH_PARAM));
            }
        });

        // when
        securedWithDeliveryIdAuthorizationFilter.filter(containerRequestContext);

        // then
        BDDMockito.verify(authorizationService).validateTokenWithDeliveryId(tokenDto, A_VALID_DELIVERY_ID);
    }

    @Test
    public void givenInvalidPathParam_whenFilter_thenAssembleException() {
        BDDMockito.given(containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION))
                .willReturn(A_HEADER_STRING);
        BDDMockito.given(containerRequestContext.getUriInfo()).willReturn(uriInfo);
        BDDMockito.given(tokenDtoAssembler.assembleFromString(A_HEADER_STRING)).willReturn(tokenDto);
        BDDMockito.given(uriInfo.getPathParameters()).willReturn(new MultivaluedHashMap<String, String>() {
            {
                put("", Arrays.asList(AN_INVALID_PATH_PARAM));
            }
        });

        // when
        securedWithDeliveryIdAuthorizationFilter.filter(containerRequestContext);

        // then
        BDDMockito.verify(httpExceptionResponseAssembler)
                .assembleResponseFromExceptionClass(ServiceBadInputParameterException.class);
    }
}
