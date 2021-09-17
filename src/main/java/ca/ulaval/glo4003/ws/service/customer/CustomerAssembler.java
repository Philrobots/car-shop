package ca.ulaval.glo4003.ws.service.customer;

import ca.ulaval.glo4003.ws.api.customer.dto.CustomerDto;
import ca.ulaval.glo4003.ws.domain.customer.Customer;
import ca.ulaval.glo4003.ws.domain.customer.CustomerFactory;
import ca.ulaval.glo4003.ws.domain.customer.exception.InvalidDateFormatException;
import ca.ulaval.glo4003.ws.domain.date.DateFormat;

import java.util.Date;

public class CustomerAssembler {

    private CustomerFactory customerFactory;
    private DateFormat dateFormat;

    public CustomerAssembler(CustomerFactory customerFactory, DateFormat dateFormat) {
        this.customerFactory = customerFactory;
        this.dateFormat = dateFormat;
    }

    public Customer DtoToCustomer(CustomerDto customerDto) throws InvalidDateFormatException {
        Date dateOfBirth = this.dateFormat.stringToDate(customerDto.birthdate);
        return this.customerFactory.create(customerDto.name, dateOfBirth, customerDto.email, customerDto.password);
    }

    public CustomerDto toDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.name = customer.getName();
        customerDto.email = customer.getEmail();
        customerDto.birthdate = this.dateFormat.dateToString(customer.getBirthdate());
        customerDto.password = customer.getPassword();
        return customerDto;
    }

}
