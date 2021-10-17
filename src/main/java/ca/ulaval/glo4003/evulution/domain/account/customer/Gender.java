package ca.ulaval.glo4003.evulution.domain.account.customer;

public enum Gender {
    MEN("M"), WOMEN("W"), OTHER("O");

    private final String sex;

    Gender(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }
}
