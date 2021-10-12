package ca.ulaval.glo4003.evulution.service.customer;

import ca.ulaval.glo4003.evulution.api.customer.dto.CustomerDto;
import ca.ulaval.glo4003.evulution.domain.account.AccountRepository;
import ca.ulaval.glo4003.evulution.domain.customer.Customer;
import ca.ulaval.glo4003.evulution.domain.customer.CustomerRepository;
import ca.ulaval.glo4003.evulution.domain.customer.AccountValidator;

public class CustomerService {

    private CustomerRepository customerRepository;
    private CustomerAssembler customerAssembler;
    private AccountValidator accountValidator;
    private AccountRepository accountRepository;

    public CustomerService(CustomerRepository customerRepository, CustomerAssembler customerAssembler,
            AccountValidator accountValidator, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.customerAssembler = customerAssembler;
        this.accountValidator = accountValidator;
        this.accountRepository = accountRepository;
    }

    public void addCustomer(CustomerDto customerDto) {
        Customer customer = this.customerAssembler.DtoToCustomer(customerDto);
        this.accountValidator.validateEmailIsNotInUse(customerDto.email);
        this.customerRepository.addCustomer(customer);
        this.accountRepository.addAccount(customer);
    }
}
