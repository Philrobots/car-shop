package ca.ulaval.glo4003.evulution.service.customer;

import ca.ulaval.glo4003.evulution.api.customer.dto.CustomerDto;
import ca.ulaval.glo4003.evulution.domain.customer.Customer;
import ca.ulaval.glo4003.evulution.domain.customer.CustomerRepository;
import ca.ulaval.glo4003.evulution.domain.customer.CustomerValidator;
import ca.ulaval.glo4003.evulution.domain.customer.exception.CustomerAlreadyExistsException;

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

    public void addCustomer(CustomerDto customerDto) throws CustomerAlreadyExistsException {
        this.customerValidator.validateEmailIsNotInUse(customerDto.email);
        Customer customer = this.customerAssembler.DtoToCustomer(customerDto);
        this.customerRepository.addAccount(customer);
    }
}
