package ca.ulaval.glo4003.evulution.domain.delivery;

import java.time.LocalDate;

public class Delivery {
    private DeliveryId deliveryId;
    private int assemblyTimeInWeeks;
    private boolean isAtCampus = false;
    private DeliveryDetails deliveryDetails;
    private LocalDate expectedDeliveryDate;

    public Delivery(DeliveryId deliveryId, int assemblyTimeInWeeks) {
        this.deliveryId = deliveryId;
        this.assemblyTimeInWeeks = assemblyTimeInWeeks;
    }

    public void deliverToCampus(){
        isAtCampus = true;
    }

    public DeliveryId getDeliveryId(){
        return deliveryId;
    }

    public void setDeliveryDetails(DeliveryDetails deliveryDetails) {
        this.deliveryDetails = deliveryDetails;
    }

    public LocalDate addDelayInWeeks(int weeks){
        this.expectedDeliveryDate = this.expectedDeliveryDate.plusWeeks(weeks);
        return this.expectedDeliveryDate;
    }

    public void setDeliveryDate(int carTimeToProduce, int batteryTimeToProduce) {
        int expectedProductionTimeInWeeks = carTimeToProduce + batteryTimeToProduce
                + this.assemblyTimeInWeeks;
        this.expectedDeliveryDate = LocalDate.now().plusWeeks(expectedProductionTimeInWeeks);
    }
}
