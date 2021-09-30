package ca.ulaval.glo4003.evulution.domain.delivery;

import ca.ulaval.glo4003.evulution.domain.delivery.exception.BadDeliveryLocationException;
import ca.ulaval.glo4003.evulution.domain.delivery.exception.BadDeliveryModeException;

import java.util.List;

public class Delivery {
    private String mode = null;
    private String location = null;
    private DeliveryId deliveryId;
    private List<String> possibleDeliveryLocation;

    public Delivery(DeliveryId deliveryId, List<String> possibleDeliveryLocation) {
        this.deliveryId = deliveryId;
        this.possibleDeliveryLocation = possibleDeliveryLocation;
    }

    public DeliveryId getDeliveryId() {
        return deliveryId;
    }

    public void chooseDeliveryLocation(String mode, String location) {
        if (!mode.equals("At campus"))
            throw new BadDeliveryModeException();
        if (!possibleDeliveryLocation.contains(location))
            throw new BadDeliveryLocationException();
        this.mode = mode;
        this.location = location;
    }
}
