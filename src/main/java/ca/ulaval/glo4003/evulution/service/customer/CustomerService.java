package ca.ulaval.glo4003.evulution.service.customer;

import ca.ulaval.glo4003.evulution.api.customer.dto.CustomerDto;
import ca.ulaval.glo4003.evulution.domain.account.AccountRepository;
import ca.ulaval.glo4003.evulution.domain.account.customer.AccountValidator;
import ca.ulaval.glo4003.evulution.domain.account.customer.Customer;

public class CustomerService {

    private AccountRepository accountRepository;
    private CustomerAssembler customerAssembler;
    private AccountValidator accountValidator;

    public CustomerService(AccountRepository accountRepository, CustomerAssembler customerAssembler,
            AccountValidator accountValidator) {
        this.accountRepository = accountRepository;
        this.customerAssembler = customerAssembler;
        this.accountValidator = accountValidator;
    }

    public void addCustomer(CustomerDto customerDto) {
        Customer customer = this.customerAssembler.DtoToCustomer(customerDto);
        this.accountValidator.validateEmailIsNotInUse(customerDto.email);
        this.accountRepository.addAccount(customer);
    }
}
