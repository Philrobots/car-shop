package ca.ulaval.glo4003.evulution.service.customer;

import ca.ulaval.glo4003.evulution.domain.account.AccountRepository;
import ca.ulaval.glo4003.evulution.domain.account.customer.Customer;
import ca.ulaval.glo4003.evulution.domain.account.customer.CustomerFactory;
import ca.ulaval.glo4003.evulution.domain.account.exceptions.InvalidDateFormatException;
import ca.ulaval.glo4003.evulution.domain.exceptions.BadInputParameterException;
import ca.ulaval.glo4003.evulution.infrastructure.account.exceptions.AccountAlreadyExistsException;
import ca.ulaval.glo4003.evulution.service.customer.dto.CustomerDto;
import ca.ulaval.glo4003.evulution.service.customer.exceptions.ServiceCustomerAlreadyExistsException;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadInputParameterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    private static final String A_NAME = "John Doe";
    private static final String A_BIRTHDATE = "1990-01-10";
    private static final String AN_EMAIL = "yuk@lamby.com";
    private static final String A_PASSWORD = "strong password";
    private static final String A_GENDER = "M";

    @Mock
    private Customer customer;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerFactory customerFactory;

    @Mock
    private CustomerDto customerDto;

    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        customerService = new CustomerService(accountRepository, customerFactory);
        customerDto.name = A_NAME;
        customerDto.birthdate = A_BIRTHDATE;
        customerDto.email = AN_EMAIL;
        customerDto.password = A_PASSWORD;
        customerDto.sex = A_GENDER;
    }

    @Test
    public void whenAddCustomer_thenCustomerFactoryCreates()
            throws InvalidDateFormatException, BadInputParameterException {
        // when
        customerService.addCustomer(customerDto);

        // then
        Mockito.verify(customerFactory).create(customerDto.name, customerDto.birthdate, customerDto.email,
                customerDto.password, customerDto.sex);

    }

    @Test
    public void whenAddCustomer_thenAccountRepositoryAddsAccount() throws InvalidDateFormatException,
            BadInputParameterException, AccountAlreadyExistsException {
        // given
        BDDMockito.given(customerFactory.create(customerDto.name, customerDto.birthdate, customerDto.email,
                customerDto.password, customerDto.sex)).willReturn(customer);

        // when
        customerService.addCustomer(customerDto);

        // then
        Mockito.verify(accountRepository).addAccount(customer);
    }

    @Test
    public void givenInvalidDateFormatException_whenAddCustomer_thenServiceBadInputParameterException()
            throws InvalidDateFormatException, BadInputParameterException {
        // given
        BDDMockito.doThrow(InvalidDateFormatException.class).when(customerFactory).create(customerDto.name,
                customerDto.birthdate, customerDto.email, customerDto.password, customerDto.sex);

        // when & then
        Assertions.assertThrows(ServiceBadInputParameterException.class,
                () -> customerService.addCustomer(customerDto));
    }

    @Test
    public void givenBadInputParameterException_whenAddCustomer_thenServiceBadInputParameterException()
            throws InvalidDateFormatException, BadInputParameterException {
        // given
        BDDMockito.doThrow(BadInputParameterException.class).when(customerFactory).create(customerDto.name,
                customerDto.birthdate, customerDto.email, customerDto.password, customerDto.sex);

        // when & then
        Assertions.assertThrows(ServiceBadInputParameterException.class,
                () -> customerService.addCustomer(customerDto));
    }

    @Test
    public void givenAccountAlreadyExistsException_whenAddCustomer_thenServiceCustomerAlreadyExistsException()
            throws InvalidDateFormatException, BadInputParameterException,
            AccountAlreadyExistsException {
        // given
        BDDMockito.given(customerFactory.create(customerDto.name, customerDto.birthdate, customerDto.email,
                customerDto.password, customerDto.sex)).willReturn(customer);
        BDDMockito.doThrow(AccountAlreadyExistsException.class).when(accountRepository).addAccount(customer);

        // when & then
        Assertions.assertThrows(ServiceCustomerAlreadyExistsException.class,
                () -> customerService.addCustomer(customerDto));
    }
}
