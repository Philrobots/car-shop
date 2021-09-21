package ca.ulaval.glo4003.evulution.api.customer;

import ca.ulaval.glo4003.evulution.api.customer.dto.CustomerDto;
import ca.ulaval.glo4003.evulution.api.customer.exception.InvalidDateFormatException;
import ca.ulaval.glo4003.evulution.api.customer.validator.DateFormatValidator;
import ca.ulaval.glo4003.evulution.domain.customer.exception.AccountAlreadyExistException;
import ca.ulaval.glo4003.evulution.service.customer.CustomerService;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerResourceImplTest {

    @Mock
    private CustomerDto customerDto;

    @Mock
    private CustomerService customerService;

    @Mock
    DateFormatValidator dateFormatValidator;

    private CustomerResourceImpl accountResource;

    @BeforeEach
    public void setUp() {
        accountResource = new CustomerResourceImpl(customerService, dateFormatValidator);
    }

    @Test
    public void whenGetAll_thenCustomerServiceGetsCustomers() {
        BDDMockito.given(customerService.getCustomers()).willReturn(Lists.newArrayList(customerDto));

        // when
        this.accountResource.getAll();

        // then
        Mockito.verify(customerService).getCustomers();
    }

    @Test
    public void whenAddCustomer_thenCustomerServiceAddsCustomer()
            throws AccountAlreadyExistException, InvalidDateFormatException {

        // when
        this.accountResource.addCustomer(customerDto);

        // then
        Mockito.verify(customerService).addCustomer(customerDto);
    }
}
