package ca.ulaval.glo4003.evulution.domain.customer;

import ca.ulaval.glo4003.evulution.domain.invoice.Invoice;

import java.time.LocalDate;

public class Customer {

    private String name;
    private LocalDate birthDate;
    private String email;
    private String password;
    private Invoice invoice;

    private Role role;
    private final Gender gender;

    public Customer(String name, LocalDate birthDate, String email, String password, Gender gender) {
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
        this.role = Role.CUSTOMER;
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
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

    public boolean isAuthenticationValid(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
