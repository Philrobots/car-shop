package ca.ulaval.glo4003.evulution.domain.delivery;

import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.DeliveryIncompleteException;

import java.time.LocalDate;
import java.util.ArrayList;

public class Delivery {
    private final Integer assemblyTimeInWeeks;
    private final DeliveryId deliveryId;
    private final ArrayList<DeliveryStatus> status = new ArrayList<>();
    private DeliveryDetails deliveryDetails;
    private LocalDate deliveryDate;

    public Delivery(DeliveryId deliveryId, Integer assemblyTimeInWeeks) {
        this.deliveryId = deliveryId;
        this.assemblyTimeInWeeks = assemblyTimeInWeeks;
        setStatus(DeliveryStatus.CREATED);
    }

    public void setStatus(DeliveryStatus status) {
        switch (status) {
        case SHIPPED:
            // TODO Ask client about potential exception if delivery not confirmed
            break;
        case COMPLETED:
            if (!this.status.contains(DeliveryStatus.CONFIRMED) || !this.status.contains(DeliveryStatus.SHIPPED)) {
                throw new DeliveryIncompleteException();
            }
        }
        this.status.add(status);
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
