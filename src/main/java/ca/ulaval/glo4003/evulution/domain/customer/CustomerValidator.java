package ca.ulaval.glo4003.evulution.domain.customer;

import ca.ulaval.glo4003.evulution.domain.customer.exception.AccountAlreadyExistException;

public class CustomerValidator {

    private CustomerRepository customerRepository;

    public CustomerValidator(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void validateEmailIsNotInUse(String email) throws AccountAlreadyExistException {
        Customer c = this.customerRepository.getAccountByEmail(email);

        if (c != null) {
            throw new AccountAlreadyExistException();
        }
    }
}
