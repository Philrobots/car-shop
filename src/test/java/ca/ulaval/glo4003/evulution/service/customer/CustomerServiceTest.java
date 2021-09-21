package ca.ulaval.glo4003.evulution.service.customer;

import ca.ulaval.glo4003.evulution.api.customer.dto.CustomerDto;
import ca.ulaval.glo4003.evulution.domain.customer.Customer;
import ca.ulaval.glo4003.evulution.domain.customer.CustomerRepository;
import ca.ulaval.glo4003.evulution.domain.customer.CustomerValidator;
import ca.ulaval.glo4003.evulution.domain.customer.exception.AccountAlreadyExistException;
import ca.ulaval.glo4003.evulution.domain.customer.exception.InvalidDateFormatException;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    private static final String AN_EMAIL = "yuk@lamby.com";

    @Mock
    private Customer customer;

    @Mock
    private CustomerDto customerDto;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerAssembler customerAssembler;

    @Mock
    private CustomerValidator customerValidator;

    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        customerDto.email = AN_EMAIL;
        customerService = new CustomerService(customerRepository, customerAssembler, customerValidator);
    }

    @Test
    public void givenACustomer_whenGetCustomers_thenCustomerRepositoryGetsAll() {
        BDDMockito.given(customerRepository.getAll()).willReturn(Lists.newArrayList(customer));

        // when
        this.customerService.getCustomers();

        // then
        Mockito.verify(customerRepository).getAll();
    }

    @Test
    public void givenAnAccountDto_whenAddCustomer_shouldCustomerRepositoryAddsTheAccount()
            throws AccountAlreadyExistException, InvalidDateFormatException {
        // given
        BDDMockito.given(customerAssembler.DtoToCustomer(customerDto)).willReturn(customer);

        // when
        this.customerService.addCustomer(customerDto);

        // then
        Mockito.verify(customerRepository).addAccount(customer);
    }

    @Test
    public void givenAnAccountDto_whenAddCustomer_ThenCustomerAssemblerAssemblesDtoToCustomer()
            throws AccountAlreadyExistException, InvalidDateFormatException {
        // given
        BDDMockito.given(customerAssembler.DtoToCustomer(customerDto)).willReturn(customer);

        // when
        this.customerService.addCustomer(customerDto);

        // then
        Mockito.verify(customerAssembler).DtoToCustomer(customerDto);
    }

    @Test
    public void givenAnAccountDto_whenAddCustomer_ThenCustomerValidatorValidatesEmailIsNotInUse()
            throws AccountAlreadyExistException, InvalidDateFormatException {
        // given
        BDDMockito.given(customerAssembler.DtoToCustomer(customerDto)).willReturn(customer);

        // when
        this.customerService.addCustomer(customerDto);

        // then
        Mockito.verify(customerValidator).validateEmailIsNotInUse(AN_EMAIL);
    }

}
