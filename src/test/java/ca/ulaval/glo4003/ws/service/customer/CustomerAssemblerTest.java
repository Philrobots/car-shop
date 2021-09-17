package ca.ulaval.glo4003.ws.service.customer;

import ca.ulaval.glo4003.ws.service.customer.CustomerAssembler;
import ca.ulaval.glo4003.ws.api.customer.dto.CustomerDto;
import ca.ulaval.glo4003.ws.domain.customer.Customer;
import ca.ulaval.glo4003.ws.domain.customer.CustomerFactory;
import ca.ulaval.glo4003.ws.domain.customer.exception.InvalidDateFormatException;
import ca.ulaval.glo4003.ws.domain.date.DateFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
public class CustomerAssemblerTest {

    private String NAME = "tiray";
    private String EMAIl = "tiray@expat.com";
    private String PASSWORD = "vinceBro@expat.com";
    private Date BIRTH_DATE = new Date();
    private String DATE_IN_STRING = "1999-08-08";

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
    public void givenAnCustomerDto_whenToCustomer_thenShouldCallTheCustomerFactory() throws InvalidDateFormatException {
        // given
        BDDMockito.given(dateFormat.stringToDate(DATE_IN_STRING)).willReturn(BIRTH_DATE);
        BDDMockito.given(customerFactory.create(NAME, BIRTH_DATE, EMAIl, PASSWORD)).willReturn(customer);
        CustomerDto customerDto = givenAnCustomerDto();

        // when
        Customer aCustomer = customerAssembler.DtoToCustomer(customerDto);

        // then
        Mockito.verify(customerFactory).create(NAME, BIRTH_DATE, EMAIl, PASSWORD);
    }

    @Test
    public void givenAnCustomerDto_whenToCustomer_thenShouldCallTheDateFormatToGetTheDate()
            throws InvalidDateFormatException {
        // given
        BDDMockito.given(dateFormat.stringToDate(DATE_IN_STRING)).willReturn(BIRTH_DATE);
        BDDMockito.given(customerFactory.create(NAME, BIRTH_DATE, EMAIl, PASSWORD)).willReturn(customer);
        CustomerDto customerDto = givenAnCustomerDto();

        // when
        Customer aCustomer = customerAssembler.DtoToCustomer(customerDto);

        // then
        Mockito.verify(dateFormat).stringToDate(DATE_IN_STRING);
    }

    @Test
    public void givenAnCustomerDto_whenToCustomer_thenTheCustomerShouldHaveTheSameInformationAsTheDto()
            throws InvalidDateFormatException {
        // given
        BDDMockito.given(dateFormat.stringToDate(DATE_IN_STRING)).willReturn(BIRTH_DATE);
        BDDMockito.given(customerFactory.create(NAME, BIRTH_DATE, EMAIl, PASSWORD)).willReturn(customer);
        BDDMockito.given(customer.getPassword()).willReturn(PASSWORD);
        BDDMockito.given(customer.getName()).willReturn(NAME);
        BDDMockito.given(customer.getEmail()).willReturn(EMAIl);
        CustomerDto customerDto = givenAnCustomerDto();

        // when
        Customer aCustomer = customerAssembler.DtoToCustomer(customerDto);

        // then
        assertEquals(aCustomer.getPassword(), PASSWORD);
        assertEquals(aCustomer.getName(), NAME);
        assertEquals(aCustomer.getEmail(), EMAIl);
    }

    @Test
    public void givenAnCustomer_whenToDto_thenCustomerShouldHaveTheSameInformationAsTheDto() {
        // given
        BDDMockito.given(customer.getPassword()).willReturn(PASSWORD);
        BDDMockito.given(customer.getName()).willReturn(NAME);
        BDDMockito.given(customer.getEmail()).willReturn(EMAIl);
        BDDMockito.given(customer.getBirthdate()).willReturn(BIRTH_DATE);
        BDDMockito.given(dateFormat.dateToString(BIRTH_DATE)).willReturn(DATE_IN_STRING);

        // when
        CustomerDto aCustomerDto = customerAssembler.toDto(customer);

        // then
        assertEquals(aCustomerDto.email, customer.getEmail());
        assertEquals(aCustomerDto.name, customer.getName());
        assertEquals(aCustomerDto.password, customer.getPassword());
        assertEquals(aCustomerDto.birthdate, DATE_IN_STRING);
    }

    private CustomerDto givenAnCustomerDto() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.email = EMAIl;
        customerDto.password = PASSWORD;
        customerDto.name = NAME;
        customerDto.birthdate = DATE_IN_STRING;
        return customerDto;
    }
}
