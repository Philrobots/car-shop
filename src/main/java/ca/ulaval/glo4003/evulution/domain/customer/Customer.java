package ca.ulaval.glo4003.evulution.domain.customer;

import java.util.Date;

public class Customer {

    private String name;
    private Date birthdate;
    private String email;
    private String password;

    public Customer(String name, Date birthdate, String email, String password) {
        this.name = name;
        this.birthdate = birthdate;
        this.email = email;
        this.password = password;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean isRightCustomerInfo(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }
}
