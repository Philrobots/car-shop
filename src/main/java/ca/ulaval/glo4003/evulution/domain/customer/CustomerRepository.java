package ca.ulaval.glo4003.evulution.domain.customer;

import java.util.List;

public interface CustomerRepository {
    void addAccount(Customer customer);

    void remove(String email);

    List<Customer> getAll();

    Customer getAccountByEmail(String email);
}
