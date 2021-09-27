package ca.ulaval.glo4003.evulution.api.validators;

import ca.ulaval.glo4003.evulution.api.exceptions.InvalidDateFormatException;

import java.util.regex.Pattern;

public class DateFormatValidator {
    private final Pattern pattern;

    public DateFormatValidator(String dateRegex) {
        this.pattern = Pattern.compile(dateRegex);
    }

    public void validate(String date) {
        if (!pattern.matcher(date).find()) {
            throw new InvalidDateFormatException();
        }
    }
}
