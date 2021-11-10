package ca.ulaval.glo4003.evulution.domain.delivery;

public enum DeliveryStatus {
    CREATED("created"), CONFIRMED("confirmed"), SHIPPED("shipped"), COMPLETED("completed");

    private final String status;

    DeliveryStatus(String deliveryStatus) {
        this.status = deliveryStatus;
    }
}
