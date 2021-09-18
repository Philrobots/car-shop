package ca.ulaval.glo4003.evulution.domain.customer;

import java.util.Date;

public class CustomerFactory {

    public Customer create(String name, Date dateOfBirth, String email, String password) {
        return new Customer(name, dateOfBirth, email, password);
    }

}
