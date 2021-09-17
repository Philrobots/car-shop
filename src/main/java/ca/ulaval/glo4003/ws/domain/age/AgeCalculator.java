package ca.ulaval.glo4003.ws.domain.age;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class AgeCalculator {

    public int calculateAge(Date date) {
        LocalDate currentDate = LocalDate.now();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(localDate, currentDate).getYears();
    }

}
