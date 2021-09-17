package ca.ulaval.glo4003.ws.service.customer;

import ca.ulaval.glo4003.ws.api.customer.dto.CustomerDto;
import ca.ulaval.glo4003.ws.domain.customer.Customer;
import ca.ulaval.glo4003.ws.domain.customer.CustomerRepository;
import ca.ulaval.glo4003.ws.domain.customer.CustomerValidator;
import ca.ulaval.glo4003.ws.domain.customer.exception.AccountAlreadyExistException;
import ca.ulaval.glo4003.ws.domain.customer.exception.InvalidDateFormatException;
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

    private String AN_EMAIL = "yuk@lamby.com";

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
    public void whenGettingAllAccount_thenShouldCallTheRepositoryToGetAccount() {
        BDDMockito.given(customerRepository.getAll()).willReturn(Lists.newArrayList(customer));

        // when
        this.customerService.getCustomers();

        // then
        Mockito.verify(customerRepository).getAll();
    }

    @Test
    public void givenAnAccountDto_whenAddingTheAddingTheAccount_shouldCallTheRepositoryToAddAccount()
            throws AccountAlreadyExistException, InvalidDateFormatException {
        // given
        BDDMockito.given(customerAssembler.DtoToCustomer(customerDto)).willReturn(customer);

        // when
        this.customerService.addCustomer(customerDto);

        // then
        Mockito.verify(customerRepository).addAccount(customer);
    }

    @Test
    public void givenAnAccountDto_whenAddingTheAddingTheAccount_shouldCallTheRepositoryToAssembleCustomer()
            throws AccountAlreadyExistException, InvalidDateFormatException {
        // given
        BDDMockito.given(customerAssembler.DtoToCustomer(customerDto)).willReturn(customer);

        // when
        this.customerService.addCustomer(customerDto);

        // then
        Mockito.verify(customerAssembler).DtoToCustomer(customerDto);
    }

    @Test
    public void givenAnAccountDto_whenAddingTheAddingTheAccount_shouldCallTheCustomerValidatorToValidateCustomer()
            throws AccountAlreadyExistException, InvalidDateFormatException {
        // given
        BDDMockito.given(customerAssembler.DtoToCustomer(customerDto)).willReturn(customer);

        // when
        this.customerService.addCustomer(customerDto);

        // then
        Mockito.verify(customerValidator).validateEmailIsNotUse(AN_EMAIL);
    }

}
