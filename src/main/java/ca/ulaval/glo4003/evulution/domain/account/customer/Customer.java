package ca.ulaval.glo4003.evulution.domain.account.customer;

import ca.ulaval.glo4003.evulution.domain.account.Account;

import java.time.LocalDate;

public class Customer extends Account {
    private final String name;
    private final LocalDate birthDate;
    private final Gender gender;

    public Customer(String name, LocalDate birthDate, String email, String password, Gender gender) {
        super(email, password);
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
    }

}
