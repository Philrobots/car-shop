package ca.ulaval.glo4003.evulution.service.customer;

import ca.ulaval.glo4003.evulution.api.customer.dto.CustomerDto;
import ca.ulaval.glo4003.evulution.api.exceptions.InvalidDateFormatException;
import ca.ulaval.glo4003.evulution.domain.account.AccountRepository;
import ca.ulaval.glo4003.evulution.domain.account.customer.AccountValidator;
import ca.ulaval.glo4003.evulution.domain.account.customer.Customer;
import ca.ulaval.glo4003.evulution.domain.account.exceptions.CustomerAlreadyExistsException;
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
    private AccountRepository accountRepository;

    @Mock
    private CustomerAssembler customerAssembler;

    @Mock
    private AccountValidator accountValidator;

    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        customerDto.email = AN_EMAIL;
        customerService = new CustomerService(accountRepository, customerAssembler, accountValidator);
    }

    @Test
    public void givenAnAccountDto_whenAddCustomer_shouldAddAccountToRepository()
            throws CustomerAlreadyExistsException, InvalidDateFormatException {
        // given
        BDDMockito.given(customerAssembler.DtoToCustomer(customerDto)).willReturn(customer);

        // when
        this.customerService.addCustomer(customerDto);

        // then
        Mockito.verify(accountRepository).addAccount(customer);
    }

    @Test
    public void givenAnAccountDto_whenAddCustomer_ThenCustomerAssemblerAssemblesDtoToCustomer()
            throws CustomerAlreadyExistsException, InvalidDateFormatException {
        // given
        BDDMockito.given(customerAssembler.DtoToCustomer(customerDto)).willReturn(customer);

        // when
        this.customerService.addCustomer(customerDto);

        // then
        Mockito.verify(customerAssembler).DtoToCustomer(customerDto);
    }

    @Test
    public void givenAnAccountDto_whenAddCustomer_ThenCustomerValidatorValidatesEmailIsNotInUse()
            throws CustomerAlreadyExistsException, InvalidDateFormatException {
        // given
        BDDMockito.given(customerAssembler.DtoToCustomer(customerDto)).willReturn(customer);

        // when
        this.customerService.addCustomer(customerDto);

        // then
        Mockito.verify(accountValidator).validateEmailIsNotInUse(AN_EMAIL);
    }

}
