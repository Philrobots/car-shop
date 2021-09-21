package ca.ulaval.glo4003.evulution.service.customer;

import ca.ulaval.glo4003.evulution.api.customer.dto.CustomerDto;
import ca.ulaval.glo4003.evulution.domain.customer.Customer;
import ca.ulaval.glo4003.evulution.domain.customer.CustomerFactory;
import ca.ulaval.glo4003.evulution.domain.customer.exception.InvalidDateFormatException;
import ca.ulaval.glo4003.evulution.domain.date.DateFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CustomerAssemblerTest {

    private static final String A_NAME = "tiray";
    private static final String AN_EMAIL = "tiray@expat.com";
    private static final String A_PASSWORD = "vinceBro@expat.com";
    private static final Date A_BIRTHDATE = new Date();
    private static final String A_DATE = "1999-08-08";

    @Mock
    private DateFormat dateFormat;

    @Mock
    private Customer customer;

    @Mock
    private CustomerFactory customerFactory;

    private CustomerAssembler customerAssembler;

    @BeforeEach
    public void setUp() throws ParseException {
        customerAssembler = new CustomerAssembler(customerFactory, dateFormat);
    }

    @Test
    public void givenACustomerDto_whenDtoToCustomer_thenCustomerFactoryIsCalled() throws InvalidDateFormatException {
        // given
        BDDMockito.given(dateFormat.stringToDate(A_DATE)).willReturn(A_BIRTHDATE);
        BDDMockito.given(customerFactory.create(A_NAME, A_BIRTHDATE, AN_EMAIL, A_PASSWORD)).willReturn(customer);
        CustomerDto customerDto = givenAnCustomerDto();

        // when
        customerAssembler.DtoToCustomer(customerDto);

        // then
        Mockito.verify(customerFactory).create(A_NAME, A_BIRTHDATE, AN_EMAIL, A_PASSWORD);
    }

    @Test
    public void givenACustomerDto_whenDtoToCustomer_thenVerifiesStringToDateConversion()
            throws InvalidDateFormatException {
        // given
        BDDMockito.given(dateFormat.stringToDate(A_DATE)).willReturn(A_BIRTHDATE);
        BDDMockito.given(customerFactory.create(A_NAME, A_BIRTHDATE, AN_EMAIL, A_PASSWORD)).willReturn(customer);
        CustomerDto customerDto = givenAnCustomerDto();

        // when
        customerAssembler.DtoToCustomer(customerDto);

        // then
        Mockito.verify(dateFormat).stringToDate(A_DATE);
    }

    @Test
    public void givenACustomerDto_whenDtoToCustomer_thenCustomersInfoMatchesDto() throws InvalidDateFormatException {
        // given
        BDDMockito.given(dateFormat.stringToDate(A_DATE)).willReturn(A_BIRTHDATE);
        BDDMockito.given(customerFactory.create(A_NAME, A_BIRTHDATE, AN_EMAIL, A_PASSWORD)).willReturn(customer);
        BDDMockito.given(customer.getPassword()).willReturn(A_PASSWORD);
        BDDMockito.given(customer.getName()).willReturn(A_NAME);
        BDDMockito.given(customer.getEmail()).willReturn(AN_EMAIL);
        CustomerDto customerDto = givenAnCustomerDto();

        // when
        Customer aCustomer = customerAssembler.DtoToCustomer(customerDto);

        // then
        assertEquals(aCustomer.getPassword(), A_PASSWORD);
        assertEquals(aCustomer.getName(), A_NAME);
        assertEquals(aCustomer.getEmail(), AN_EMAIL);
    }

    @Test
    public void givenACustomer_whenCustomerToDto_thenDtoInfoMatchesCustomer() {
        // given
        BDDMockito.given(customer.getPassword()).willReturn(A_PASSWORD);
        BDDMockito.given(customer.getName()).willReturn(A_NAME);
        BDDMockito.given(customer.getEmail()).willReturn(AN_EMAIL);
        BDDMockito.given(customer.getBirthDate()).willReturn(A_BIRTHDATE);
        BDDMockito.given(dateFormat.dateToString(A_BIRTHDATE)).willReturn(A_DATE);

        // when
        CustomerDto aCustomerDto = customerAssembler.CustomerToDto(customer);

        // then
        assertEquals(aCustomerDto.email, customer.getEmail());
        assertEquals(aCustomerDto.name, customer.getName());
        assertEquals(aCustomerDto.password, customer.getPassword());
        assertEquals(aCustomerDto.birthdate, A_DATE);
    }

    private CustomerDto givenAnCustomerDto() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.email = AN_EMAIL;
        customerDto.password = A_PASSWORD;
        customerDto.name = A_NAME;
        customerDto.birthdate = A_DATE;
        return customerDto;
    }
}
