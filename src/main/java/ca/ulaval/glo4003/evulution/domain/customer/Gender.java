package ca.ulaval.glo4003.evulution.domain.customer;

import ca.ulaval.glo4003.evulution.api.exceptions.BadInputParameterException;

public enum Gender {
    MEN("M"), WOMEN("W"), OTHER("0");

    private String sex;

    Gender(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }
}
