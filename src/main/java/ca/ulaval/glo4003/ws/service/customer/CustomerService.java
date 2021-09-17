package ca.ulaval.glo4003.ws.service.customer;

import ca.ulaval.glo4003.ws.api.customer.dto.CustomerDto;
import ca.ulaval.glo4003.ws.domain.customer.Customer;
import ca.ulaval.glo4003.ws.domain.customer.CustomerRepository;
import ca.ulaval.glo4003.ws.domain.customer.CustomerValidator;
import ca.ulaval.glo4003.ws.domain.customer.exception.AccountAlreadyExistException;
import ca.ulaval.glo4003.ws.domain.customer.exception.InvalidDateFormatException;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerService {

    private CustomerRepository customerRepository;
    private CustomerAssembler customerAssembler;
    private CustomerValidator customerValidator;

    public CustomerService(CustomerRepository customerRepository, CustomerAssembler customerAssembler,
            CustomerValidator customerValidator) {
        this.customerRepository = customerRepository;
        this.customerAssembler = customerAssembler;
        this.customerValidator = customerValidator;
    }

    public void addCustomer(CustomerDto customerDto) throws AccountAlreadyExistException, InvalidDateFormatException {
        this.customerValidator.validateEmailIsNotUse(customerDto.email);
        Customer customer = this.customerAssembler.DtoToCustomer(customerDto);
        this.customerRepository.addAccount(customer);
    }

    public List<CustomerDto> getCustomers() {
        List<Customer> customers = this.customerRepository.getAll();
        return customers.stream().map(customerAssembler::toDto).collect(Collectors.toList());
    }
}
