package ca.ulaval.glo4003.evulution.domain.customer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomerFactory {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public Customer create(String name, String birthdate, String email, String password) {
        LocalDate date = LocalDate.parse(birthdate, formatter);
        return new Customer(name, date, email, password);
    }

}
