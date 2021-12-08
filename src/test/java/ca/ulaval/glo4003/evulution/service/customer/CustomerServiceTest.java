package ca.ulaval.glo4003.evulution.service.customer;

import ca.ulaval.glo4003.evulution.domain.account.AccountRepository;
import ca.ulaval.glo4003.evulution.domain.account.customer.Customer;
import ca.ulaval.glo4003.evulution.domain.account.customer.CustomerFactory;
import ca.ulaval.glo4003.evulution.service.customer.dto.CustomerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
    }

    /*
     * @Test public void givenCustomerDto_whenAddCustomer_thenAccountRepositoryAddsAccount() throws
     * CustomerAlreadyExistsException, InvalidDateFormatException, BadInputParameterException, AccountNotFoundException
     * { // given setUpCustomerDto(); BDDMockito.given(customerFactory.create(A_NAME, A_BIRTHDATE, AN_EMAIL, A_PASSWORD,
     * A_GENDER)) .willReturn(customer);
     *
     * // when this.customerService.addCustomer(customerDto);
     *
     * // then Mockito.verify(accountRepository).addAccount(customer); }
     *
     * @Test public void givenCustomerDto_whenAddCustomer_thenCustomerFactoryCreates() throws
     * InvalidDateFormatException, BadInputParameterException, CustomerAlreadyExistsException, AccountNotFoundException
     * { // given setUpCustomerDto();
     *
     * // when this.customerService.addCustomer(customerDto);
     *
     * // then Mockito.verify(customerFactory).create(A_NAME, A_BIRTHDATE, AN_EMAIL, A_PASSWORD, A_GENDER); }
     *
     * private void setUpCustomerDto() { BDDMockito.given(customerDto.name).willReturn(A_NAME);
     * BDDMockito.given(customerDto.birthdate).willReturn(A_BIRTHDATE);
     * BDDMockito.given(customerDto.email).willReturn(AN_EMAIL);
     * BDDMockito.given(customerDto.password).willReturn(A_PASSWORD);
     * BDDMockito.given(customerDto.sex).willReturn(A_GENDER); }
     *
     */
}
