package ca.ulaval.glo4003.evulution.domain.date;

import ca.ulaval.glo4003.evulution.domain.customer.exception.InvalidDateFormatException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
    private String dateRegex = "\\d{4}-\\d{2}-\\d{2}";
    private String dateFormat = "yyyy-MM-dd";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.dateFormat);

    public Date stringToDate(String date) throws InvalidDateFormatException {
        this.validateDateIsInTheCorrectFormat(date);

        try {
            return this.simpleDateFormat.parse(date);
        } catch (ParseException e) {
            throw new InvalidDateFormatException("bad input parameter");
        }
    }

    private void validateDateIsInTheCorrectFormat(String date) throws InvalidDateFormatException {
        if (!date.matches(this.dateRegex)) {
            throw new InvalidDateFormatException("bad input parameter");
        }
    }

    public String dateToString(Date date) {
        return date.toString();
    }

}
