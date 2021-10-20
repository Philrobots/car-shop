package ca.ulaval.glo4003.evulution.domain.delivery;

import java.time.LocalDate;

public class Delivery {
    private final Integer assemblyTimeInWeeks;
    private final DeliveryId deliveryId;

    private boolean isAtCampus = false;
    private DeliveryDetails deliveryDetails;
    private LocalDate deliveryDate;

    public Delivery(DeliveryId deliveryId, Integer assemblyTimeInWeeks) {
        this.deliveryId = deliveryId;
        this.assemblyTimeInWeeks = assemblyTimeInWeeks;
    }

    public void deliverToCampus() {
        isAtCampus = true;
    }

    public DeliveryId getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryDetails(DeliveryDetails deliveryDetails) {
        this.deliveryDetails = deliveryDetails;
    }

    public LocalDate addDelayInWeeks(Integer weeks) {
        this.deliveryDate = this.deliveryDate.plusWeeks(weeks);
        return this.deliveryDate;
    }

    public void calculateDeliveryDate(Integer carTimeToProduce, Integer batteryTimeToProduce) {
        int expectedProductionTimeInWeeks = carTimeToProduce + batteryTimeToProduce + this.assemblyTimeInWeeks;
        this.deliveryDate = LocalDate.now().plusWeeks(expectedProductionTimeInWeeks);
    }
}
