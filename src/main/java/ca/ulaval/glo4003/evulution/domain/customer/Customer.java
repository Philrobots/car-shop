package ca.ulaval.glo4003.evulution.domain.customer;

import ca.ulaval.glo4003.evulution.domain.account.Account;
import ca.ulaval.glo4003.evulution.domain.invoice.Invoice;

import java.time.LocalDate;

public class Customer extends Account {

    private String name;
    private LocalDate birthDate;
    private Invoice invoice;
    private Gender gender;

    public Customer(String name, LocalDate birthDate, String email, String password, Gender gender) {
        super(email, password);
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getName() {
        return name;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
