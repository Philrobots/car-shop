package ca.ulaval.glo4003.evulution.domain.customer;

import ca.ulaval.glo4003.evulution.api.exceptions.BadInputParameterException;

public class GenderFactory {

    public Gender create(String gender) {
        if (gender.equals("M")) {
            return Gender.MEN;
        }

        if (gender.equals("W")) {
            return Gender.WOMEN;
        }

        if (gender.equals("O")) {
            return Gender.OTHER;
        }

        throw new BadInputParameterException();
    }

    public String genderDto(Gender gender) {
        return gender.getSex();
    }
}
