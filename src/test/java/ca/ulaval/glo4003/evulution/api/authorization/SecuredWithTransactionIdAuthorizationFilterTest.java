package ca.ulaval.glo4003.evulution.api.authorization;

import ca.ulaval.glo4003.evulution.api.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDto;
import ca.ulaval.glo4003.evulution.api.authorization.dto.TokenDtoAssembler;
import ca.ulaval.glo4003.evulution.api.exceptions.BadInputParameterException;
import ca.ulaval.glo4003.evulution.service.authorization.AuthorizationService;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SecuredWithTransactionIdAuthorizationFilterTest {

    private final String A_HEADER_STRING = "ASDF";
    private final String A_VALID_PATH_PARAM = "1";
    private final int A_VALID_TRANSACTION_ID = 1;
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

    SecuredWithTransactionIdAuthorizationFilter securedWithTransactionIdAuthorizationFilter;

    @BeforeEach
    void setUp() {
        securedWithTransactionIdAuthorizationFilter = new SecuredWithTransactionIdAuthorizationFilter(authorizationService, tokenDtoAssembler, httpExceptionResponseAssembler);
    }

    @Test
    public void whenFilter_thenAuthorizationServiceIsCalled(){
        BDDMockito.given(containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION)).willReturn(A_HEADER_STRING);
        BDDMockito.given(containerRequestContext.getUriInfo()).willReturn(uriInfo);
        BDDMockito.given(tokenDtoAssembler.assembleFromString(A_HEADER_STRING)).willReturn(tokenDto);
        BDDMockito.given(uriInfo.getPathParameters()).willReturn(new MultivaluedHashMap<String, String>() {{
            put("", Arrays.asList(A_VALID_PATH_PARAM));
        }});

        // when
        securedWithTransactionIdAuthorizationFilter.filter(containerRequestContext);

        // then
        BDDMockito.verify(authorizationService).validateTokenWithTransactionId(tokenDto, A_VALID_TRANSACTION_ID);
    }

    @Test
    public void givenInvalidPathParam_whenFilter_thenAssembleException(){
        BDDMockito.given(containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION)).willReturn(A_HEADER_STRING);
        BDDMockito.given(containerRequestContext.getUriInfo()).willReturn(uriInfo);
        BDDMockito.given(tokenDtoAssembler.assembleFromString(A_HEADER_STRING)).willReturn(tokenDto);
        BDDMockito.given(uriInfo.getPathParameters()).willReturn(new MultivaluedHashMap<String, String>() {{
            put("", Arrays.asList(AN_INVALID_PATH_PARAM));
        }});

        // when
        securedWithTransactionIdAuthorizationFilter.filter(containerRequestContext);

        // then
        BDDMockito.verify(httpExceptionResponseAssembler).assembleResponseFromExceptionClass(BadInputParameterException.class);
    }
}