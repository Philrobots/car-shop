package ca.ulaval.glo4003.evulution.service.customer;

import ca.ulaval.glo4003.evulution.api.customer.dto.CustomerDto;
import ca.ulaval.glo4003.evulution.api.exceptions.InvalidDateFormatException;
import ca.ulaval.glo4003.evulution.domain.customer.Customer;
import ca.ulaval.glo4003.evulution.domain.customer.CustomerFactory;
import ca.ulaval.glo4003.evulution.domain.customer.Gender;
import ca.ulaval.glo4003.evulution.domain.customer.GenderFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
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
    private final Gender A_GENDER = Gender.MEN;
    private final String A_STRING_GENDER = "W";

    @Mock
    private Customer customer;

    @Mock
    private CustomerFactory customerFactory;

    @Mock
    private GenderFactory genderFactory;

    private CustomerAssembler customerAssembler;

    @BeforeEach
    public void setUp() throws ParseException {
        customerAssembler = new CustomerAssembler(customerFactory, genderFactory);
    }

    @Test
    public void givenACustomerDto_whenDtoToCustomer_thenCustomerFactoryIsCalled() throws InvalidDateFormatException {
        // given
        BDDMockito.given(genderFactory.create(A_STRING_GENDER)).willReturn(A_GENDER);
        BDDMockito.given(customerFactory.create(A_NAME, A_DATE, AN_EMAIL, A_PASSWORD, A_GENDER)).willReturn(customer);
        CustomerDto customerDto = givenAnCustomerDto();

        // when
        customerAssembler.DtoToCustomer(customerDto);

        // then
        Mockito.verify(customerFactory).create(A_NAME, A_DATE, AN_EMAIL, A_PASSWORD, A_GENDER);
    }

    @Test
    public void givenACustomerDto_whenDtoToCustomer_thenCustomersInfoMatchesDto() throws InvalidDateFormatException {
        // given
        BDDMockito.given(genderFactory.create(A_STRING_GENDER)).willReturn(A_GENDER);
        BDDMockito.given(customerFactory.create(A_NAME, A_DATE, AN_EMAIL, A_PASSWORD, A_GENDER)).willReturn(customer);
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

    @Test
    public void givenACustomerDto_whenDtoToCustomer_thenShouldCallTheGenderFactoryToCreatGenderObject() {
        // given
        BDDMockito.given(genderFactory.create(A_STRING_GENDER)).willReturn(A_GENDER);
        BDDMockito.given(customerFactory.create(A_NAME, A_DATE, AN_EMAIL, A_PASSWORD, A_GENDER)).willReturn(customer);

        CustomerDto customerDto = givenAnCustomerDto();

        // when
        Customer aCustomer = customerAssembler.DtoToCustomer(customerDto);

        // then
        Mockito.verify(genderFactory).create(A_STRING_GENDER);

    }

    @Test
    public void givenACustomer_whenCustomerToDto_thenDtoInfoMatchesCustomer() {
        // given
        BDDMockito.given(customer.getPassword()).willReturn(A_PASSWORD);
        BDDMockito.given(customer.getName()).willReturn(A_NAME);
        BDDMockito.given(customer.getEmail()).willReturn(AN_EMAIL);
        BDDMockito.given(customer.getBirthDate()).willReturn(A_LOCAL_DATE);
        BDDMockito.given(customer.getGender()).willReturn(A_GENDER);
        BDDMockito.given(genderFactory.genderDto(A_GENDER)).willReturn(A_STRING_GENDER);

        // when
        CustomerDto aCustomerDto = customerAssembler.CustomerToDto(customer);

        // then
        assertEquals(aCustomerDto.email, customer.getEmail());
        assertEquals(aCustomerDto.name, customer.getName());
        assertEquals(aCustomerDto.password, customer.getPassword());
        assertEquals(aCustomerDto.birthdate, A_DATE);
        assertEquals(aCustomerDto.sex, A_STRING_GENDER);
    }

    @Test
    public void givenACustomer_whenCustomerToDto_thenShouldCallTheGenderFactoryToCreateStringGender() {
        // given
        BDDMockito.given(customer.getPassword()).willReturn(A_PASSWORD);
        BDDMockito.given(customer.getName()).willReturn(A_NAME);
        BDDMockito.given(customer.getEmail()).willReturn(AN_EMAIL);
        BDDMockito.given(customer.getBirthDate()).willReturn(A_LOCAL_DATE);
        BDDMockito.given(customer.getGender()).willReturn(A_GENDER);
        BDDMockito.given(genderFactory.genderDto(A_GENDER)).willReturn(A_STRING_GENDER);

        // when
        CustomerDto aCustomerDto = customerAssembler.CustomerToDto(customer);

        // then
        Mockito.verify(genderFactory).genderDto(A_GENDER);

    }

    private CustomerDto givenAnCustomerDto() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.email = AN_EMAIL;
        customerDto.password = A_PASSWORD;
        customerDto.name = A_NAME;
        customerDto.birthdate = A_DATE;
        customerDto.sex = A_STRING_GENDER;
        return customerDto;
    }
}
