package ca.ulaval.glo4003.evulution.domain.invoice;

public enum Frequency {
    WEEKLY("weekly"), MONTHLY("monthly"), BIWEEKLY("biweekly");

    private final String frequency;

    Frequency(String frequency) {
        this.frequency = frequency;
    }
}
