package ca.ulaval.glo4003.evulution.domain.customer;

public interface CustomerRepository {
    void addAccount(Customer customer);

    Customer getAccountByEmail(String email);
}
