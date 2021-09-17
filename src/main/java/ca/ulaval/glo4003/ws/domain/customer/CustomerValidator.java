package ca.ulaval.glo4003.ws.domain.customer;

import ca.ulaval.glo4003.ws.domain.customer.exception.AccountAlreadyExistException;

public class CustomerValidator {

    private CustomerRepository customerRepository;

    public CustomerValidator(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void validateEmailIsNotUse(String email) throws AccountAlreadyExistException {
        Customer c = this.customerRepository.getAccountByEmail(email);

        if (c != null) {
            throw new AccountAlreadyExistException("an existing customer already exists");
        }
    }
}
