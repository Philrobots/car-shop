package ca.ulaval.glo4003.evulution.api.customer;

import ca.ulaval.glo4003.evulution.api.customer.assemblers.CustomerDtoAssembler;
import ca.ulaval.glo4003.evulution.api.customer.dto.CustomerRequest;
import ca.ulaval.glo4003.evulution.api.mappers.assemblers.HTTPExceptionResponseAssembler;
import ca.ulaval.glo4003.evulution.api.validators.ConstraintsValidator;
import ca.ulaval.glo4003.evulution.service.customer.CustomerService;
import ca.ulaval.glo4003.evulution.service.customer.dto.CustomerDto;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerResourceTest {
    private final int VALID_STATUS = 201;

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

    @Test
    public void whenAddCustomer_thenReturns201(){
        // given
        BDDMockito.given(customerDtoAssembler.fromRequest(customerRequest)).willReturn(customerDto);

        // when
        Response response = this.accountResource.addCustomer(customerRequest);

        // then
        Assertions.assertEquals(VALID_STATUS, response.getStatus());
    }
}
