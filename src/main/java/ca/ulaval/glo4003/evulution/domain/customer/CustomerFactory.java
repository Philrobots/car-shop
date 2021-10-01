package ca.ulaval.glo4003.evulution.domain.customer;

import ca.ulaval.glo4003.evulution.api.exceptions.BadInputParameterException;
import ca.ulaval.glo4003.evulution.api.exceptions.InvalidDateFormatException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomerFactory {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public Customer create(String name, String birthdate, String email, String password, String sex) {
        LocalDate date = validateDate(birthdate);
        for (Gender gender : Gender.values()) {
            if (gender.getSex().equals(sex)) {
                return new Customer(name, date, email, password, gender);
            }
        }
        throw new BadInputParameterException();
    }

    private LocalDate validateDate(String birthdate) {
        LocalDate date = LocalDate.parse(birthdate, formatter);
        if (date.isAfter(LocalDate.now())) {
            throw new InvalidDateFormatException();
        }
        return date;
    }
}
