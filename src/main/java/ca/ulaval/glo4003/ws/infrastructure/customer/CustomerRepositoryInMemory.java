package ca.ulaval.glo4003.ws.infrastructure.customer;

import ca.ulaval.glo4003.ws.domain.customer.Customer;
import ca.ulaval.glo4003.ws.domain.customer.CustomerRepository;

import java.util.*;

public class CustomerRepositoryInMemory implements CustomerRepository {

    private Map<String, Customer> accounts = new HashMap<String, Customer>();

    @Override
    public void addAccount(Customer customer) {
        String email = customer.getEmail();
        accounts.put(email, customer);
    }

    @Override
    public void remove(String email) {
        accounts.remove(email);
    }

    @Override
    public List<Customer> getAll() {
        return new ArrayList<>(this.accounts.values());
    }

    @Override
    public Customer getAccountByEmail(String email) {
        return this.accounts.get(email);
    }
}
