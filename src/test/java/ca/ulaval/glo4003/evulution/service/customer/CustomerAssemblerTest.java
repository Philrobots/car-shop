package ca.ulaval.glo4003.evulution.service.customer;

import ca.ulaval.glo4003.evulution.api.customer.dto.CustomerDto;
import ca.ulaval.glo4003.evulution.api.exceptions.InvalidDateFormatException;
import ca.ulaval.glo4003.evulution.domain.account.customer.Customer;
import ca.ulaval.glo4003.evulution.domain.account.customer.CustomerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CustomerAssemblerTest {
    private static final String A_NAME = "tiray";
    private static final String AN_EMAIL = "tiray@expat.com";
    private static final String A_PASSWORD = "vinceBro@expat.com";
    private static final String A_DATE = "1999-08-08";

    private final String DATE_FORMAT = "yyyy-MM-dd";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private final LocalDate A_LOCAL_DATE = LocalDate.parse(A_DATE, formatter);
    private final String A_VALID_GENDER = "M";

    @Mock
    private Customer customer;

    @Mock
    private CustomerFactory customerFactory;

    private CustomerAssembler customerAssembler;

    @BeforeEach
    public void setUp() {
        customerAssembler = new CustomerAssembler(customerFactory);
    }

    @Test
    public void givenACustomerDto_whenDtoToCustomer_thenCustomerFactoryIsCalled() throws InvalidDateFormatException {
        // given
        BDDMockito.given(customerFactory.create(A_NAME, A_DATE, AN_EMAIL, A_PASSWORD, A_VALID_GENDER))
                .willReturn(customer);
        CustomerDto customerDto = givenAnCustomerDto();

        // when
        customerAssembler.DtoToCustomer(customerDto);

        // then
        Mockito.verify(customerFactory).create(A_NAME, A_DATE, AN_EMAIL, A_PASSWORD, A_VALID_GENDER);
    }

    @Test
    public void givenACustomerDto_whenDtoToCustomer_thenCustomersInfoMatchesDto() throws InvalidDateFormatException {
        // given
        BDDMockito.given(customerFactory.create(A_NAME, A_DATE, AN_EMAIL, A_PASSWORD, A_VALID_GENDER))
                .willReturn(customer);
        BDDMockito.given(customer.getPassword()).willReturn(A_PASSWORD);
        BDDMockito.given(customer.getName()).willReturn(A_NAME);
        BDDMockito.given(customer.getEmail()).willReturn(AN_EMAIL);
        BDDMockito.given(customer.getBirthDate()).willReturn(A_LOCAL_DATE);
        CustomerDto customerDto = givenAnCustomerDto();

        // when
        Customer aCustomer = customerAssembler.DtoToCustomer(customerDto);

        // then
        assertEquals(aCustomer.getPassword(), A_PASSWORD);
        assertEquals(aCustomer.getName(), A_NAME);
        assertEquals(aCustomer.getEmail(), AN_EMAIL);
        assertEquals(aCustomer.getBirthDate(), A_LOCAL_DATE);
    }

    private CustomerDto givenAnCustomerDto() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.email = AN_EMAIL;
        customerDto.password = A_PASSWORD;
        customerDto.name = A_NAME;
        customerDto.birthdate = A_DATE;
        customerDto.sex = A_VALID_GENDER;
        return customerDto;
    }
}
