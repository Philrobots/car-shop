package ca.ulaval.glo4003.evulution.domain.delivery;

public class Delivery {
    private String mode = null;
    private String location = null;
    private boolean isAtCampus = false;

    public Delivery(String mode, String location) {
        this.mode = mode;
        this.location = location;
    }

    public void deliverToCampus() {
        isAtCampus = true;
    }
}
