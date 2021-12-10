package ca.ulaval.glo4003.evulution.domain.account.customer;

import ca.ulaval.glo4003.evulution.domain.account.exceptions.InvalidDateFormatException;
import ca.ulaval.glo4003.evulution.domain.exceptions.BadInputParameterException;
import ca.ulaval.glo4003.evulution.infrastructure.account.exceptions.AccountNotFoundException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomerFactory {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public Customer create(String name, String birthdate, String email, String password, String sex)
            throws InvalidDateFormatException, BadInputParameterException {
        LocalDate date = validateDate(birthdate);
        for (Gender gender : Gender.values()) {
            if (gender.getSex().equals(sex)) {
                return new Customer(name, date, email, password, gender);
            }
        }
        throw new BadInputParameterException();
    }

    private LocalDate validateDate(String birthdate) throws InvalidDateFormatException {
        LocalDate date = LocalDate.parse(birthdate, formatter);
        if (date.isAfter(LocalDate.now())) {
            throw new InvalidDateFormatException();
        }
        return date;
    }
}
