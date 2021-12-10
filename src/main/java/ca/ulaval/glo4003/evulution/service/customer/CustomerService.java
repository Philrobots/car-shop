package ca.ulaval.glo4003.evulution.service.customer;

import ca.ulaval.glo4003.evulution.domain.account.AccountRepository;
import ca.ulaval.glo4003.evulution.domain.account.customer.Customer;
import ca.ulaval.glo4003.evulution.domain.account.customer.CustomerFactory;
import ca.ulaval.glo4003.evulution.domain.account.exceptions.InvalidDateFormatException;
import ca.ulaval.glo4003.evulution.domain.exceptions.BadInputParameterException;
import ca.ulaval.glo4003.evulution.infrastructure.account.exceptions.AccountAlreadyExistsException;
import ca.ulaval.glo4003.evulution.infrastructure.account.exceptions.AccountNotFoundException;
import ca.ulaval.glo4003.evulution.service.customer.dto.CustomerDto;
import ca.ulaval.glo4003.evulution.service.customer.exceptions.ServiceCustomerAlreadyExistsException;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadInputParameterException;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadRequestException;

public class CustomerService {
    private AccountRepository accountRepository;
    private CustomerFactory customerFactory;

    public CustomerService(AccountRepository accountRepository, CustomerFactory customerFactory) {
        this.accountRepository = accountRepository;
        this.customerFactory = customerFactory;
    }

    public void addCustomer(CustomerDto customerDto) {
        try {
            Customer customer = this.customerFactory.create(customerDto.name, customerDto.birthdate, customerDto.email,
                    customerDto.password, customerDto.sex);
            this.accountRepository.addAccount(customer);
        } catch (AccountAlreadyExistsException e) {
            throw new ServiceCustomerAlreadyExistsException();
        } catch (InvalidDateFormatException | BadInputParameterException e) {
            throw new ServiceBadInputParameterException();
        }
    }
}
