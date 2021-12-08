package ca.ulaval.glo4003.evulution.api.customer;

import ca.ulaval.glo4003.evulution.api.customer.assemblers.CustomerDtoAssembler;
import ca.ulaval.glo4003.evulution.api.customer.dto.CustomerRequest;
import ca.ulaval.glo4003.evulution.api.mappers.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.validators.ConstraintsValidator;
import ca.ulaval.glo4003.evulution.service.customer.CustomerService;
import ca.ulaval.glo4003.evulution.service.customer.dto.CustomerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerResourceTest {
    private static final String AN_EMAIL = "kevin@expat.com";
    private static final String A_PASSWORD = "Fireball";
    private static final String A_NAME = "JaggerBom";
    private static final String A_BIRTH_DATE = "1992-01-01";
    private static final Integer BAD_INPUT_PARAMETERS_ERROR_CODE = 400;
    private static final String BAD_INPUT_PARAMETERS_ERROR_MESSAGE = "bad input parameter";

    @Mock
    private CustomerService customerService;

    @Mock
    private HTTPExceptionResponseAssembler httpExceptionResponseAssembler;

    @Mock
    private CustomerDtoAssembler customerDtoAssembler;

    @Mock
    private CustomerRequest customerRequest;

    @Mock
    private ConstraintsValidator constraintsValidator;

    @Mock
    private CustomerDto customerDto;

    private CustomerResource accountResource;

    @BeforeEach
    public void setUp() {
        accountResource = new CustomerResource(customerService, httpExceptionResponseAssembler, customerDtoAssembler,
                constraintsValidator);
    }

    @Test
    public void whenAddCustomer_thenConstraintsValidatorValidates() {
        // when
        this.accountResource.addCustomer(customerRequest);

        // then
        Mockito.verify(constraintsValidator).validate(customerRequest);
    }

    @Test
    public void whenAddCustomer_thenCustomerDtoAssemblesFromRequest() {
        // when
        this.accountResource.addCustomer(customerRequest);

        // then
        Mockito.verify(customerDtoAssembler).fromRequest(customerRequest);
    }

    @Test
    public void whenAddCustomer_thenCustomerServiceAddsCustomer() {
        // given
        BDDMockito.given(customerDtoAssembler.fromRequest(customerRequest)).willReturn(customerDto);

        // when
        this.accountResource.addCustomer(customerRequest);

        // then
        Mockito.verify(customerService).addCustomer(customerDto);
    }
}
