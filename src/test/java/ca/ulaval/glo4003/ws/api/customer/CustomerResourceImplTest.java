package ca.ulaval.glo4003.ws.api.customer;

import ca.ulaval.glo4003.ws.api.customer.dto.CustomerDto;
import ca.ulaval.glo4003.ws.domain.customer.exception.InvalidDateFormatException;
import ca.ulaval.glo4003.ws.service.customer.CustomerService;
import ca.ulaval.glo4003.ws.domain.customer.exception.AccountAlreadyExistException;
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

    private CustomerResourceImpl accountResource;

    @BeforeEach
    public void set_up() {
        accountResource = new CustomerResourceImpl(customerService);
    }

    @Test
    public void when_getAllAccounts_thenShouldCallTheAccountServiceToGetAllAccounts() {
        BDDMockito.given(customerService.getCustomers()).willReturn(Lists.newArrayList(customerDto));

        // when
        this.accountResource.getAll();

        // then
        Mockito.verify(customerService).getCustomers();
    }

    @Test
    public void givenAnAccountDto_whenAddAccount_thenShouldCallTheServiceToAddAccount()
            throws AccountAlreadyExistException, InvalidDateFormatException {
        // when
        this.accountResource.addCustomer(customerDto);

        // then
        Mockito.verify(customerService).addCustomer(customerDto);
    }
}
