package ca.ulaval.glo4003.evulution.api.customer;

import ca.ulaval.glo4003.evulution.api.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.customer.dto.CustomerDto;
import ca.ulaval.glo4003.evulution.api.exceptions.BadInputParameterException;
import ca.ulaval.glo4003.evulution.api.exceptions.InvalidDateFormatException;
import ca.ulaval.glo4003.evulution.api.mappers.HTTPExceptionMapper;
import ca.ulaval.glo4003.evulution.api.validators.ConstraintsValidator;
import ca.ulaval.glo4003.evulution.api.validators.DateFormatValidator;
import ca.ulaval.glo4003.evulution.service.customer.CustomerService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CustomerResourceImplTest {
    private static final String AN_EMAIL = "kevin@expat.com";
    private static final String A_PASSWORD = "Fireball";
    private static final String A_NAME = "JaggerBom";
    private static final String A_BIRTH_DATE = "1992-01-01";

    private static final Integer BAD_INPUT_PARAMETERS_ERROR_CODE = 400;
    private static final String BAD_INPUT_PARAMETERS_ERROR_MESSAGE = "bad input parameter";

    @Mock
    private CustomerService customerService;

    @Mock
    private DateFormatValidator dateFormatValidator;

    @Mock
    private ConstraintsValidator constraintsValidator;

    private CustomerDto customerDto;

    private HTTPExceptionResponseAssembler httpExceptionResponseAssembler;

    private CustomerResourceImpl accountResource;

    @BeforeEach
    public void setUp() {
        customerDto = new CustomerDto();
        httpExceptionResponseAssembler = new HTTPExceptionResponseAssembler(new HTTPExceptionMapper());
        accountResource = new CustomerResourceImpl(customerService, dateFormatValidator, httpExceptionResponseAssembler,
                constraintsValidator);
    }

    @Test
    public void givenInvalidCustomerDto_whenAddCustomer_thenExpectBadInputParametersResponse() {
        // given
        customerDto.name = A_NAME;
        customerDto.birthdate = A_BIRTH_DATE;
        customerDto.password = A_PASSWORD;
        customerDto.email = AN_EMAIL;
        Mockito.doThrow(BadInputParameterException.class).when(constraintsValidator).validate(customerDto);

        // when
        Response response = this.accountResource.addCustomer(customerDto);

        // then
        assertEquals(response.getStatus(), BAD_INPUT_PARAMETERS_ERROR_CODE);
        assertEquals(response.getEntity(), BAD_INPUT_PARAMETERS_ERROR_MESSAGE);
    }

    @Test
    public void givenInvalidBirthdate_whenAddCustomer_thenThrowInvalidDateFormatException() {
        // given
        customerDto.name = A_NAME;
        customerDto.email = AN_EMAIL;
        customerDto.birthdate = "19920101";
        customerDto.password = A_PASSWORD;
        Mockito.doThrow(InvalidDateFormatException.class).when(dateFormatValidator).validate(customerDto.birthdate);

        // when
        Response response = this.accountResource.addCustomer(customerDto);

        // then
        assertEquals(response.getStatus(), BAD_INPUT_PARAMETERS_ERROR_CODE);
        assertEquals(response.getEntity(), BAD_INPUT_PARAMETERS_ERROR_MESSAGE);
    }

    @Test
    public void givenValidCustomerDto_whenAddCustomer_thenCustomerServiceAddsCustomer() {
        // given
        customerDto.name = A_NAME;
        customerDto.email = AN_EMAIL;
        customerDto.birthdate = A_BIRTH_DATE;
        customerDto.password = A_PASSWORD;

        // when
        this.accountResource.addCustomer(customerDto);

        // then
        Mockito.verify(customerService).addCustomer(customerDto);
    }
}
