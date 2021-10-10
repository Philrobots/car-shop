package ca.ulaval.glo4003.evulution.domain.customer;

public interface CustomerRepository {
    void addCustomer(Customer customer);

    Customer getCustomerByEmail(String email);
}
