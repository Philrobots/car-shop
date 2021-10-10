package ca.ulaval.glo4003.evulution.infrastructure.customer;

import ca.ulaval.glo4003.evulution.domain.customer.Customer;
import ca.ulaval.glo4003.evulution.domain.customer.CustomerRepository;

import java.util.HashMap;
import java.util.Map;

public class CustomerRepositoryInMemory implements CustomerRepository {

    private Map<String, Customer> customers = new HashMap<String, Customer>();

    @Override
    public void addCustomer(Customer customer) {
        String email = customer.getEmail();
        customers.put(email, customer);
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        return this.customers.get(email);
    }
}
