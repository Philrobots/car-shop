package ca.ulaval.glo4003.evulution.domain.delivery;

import ca.ulaval.glo4003.evulution.domain.delivery.exception.BadDeliveryLocationException;
import ca.ulaval.glo4003.evulution.domain.delivery.exception.BadDeliveryModeException;

import java.util.List;

public class DeliveryFactory {
    DeliveryIdFactory deliveryIdFactory;
    List<String> possibleDeliveryLocation;

    public DeliveryFactory(DeliveryIdFactory deliveryIdFactory, List<String> possibleDeliveryLocation) {
        this.deliveryIdFactory = deliveryIdFactory;
        this.possibleDeliveryLocation = possibleDeliveryLocation;
    }

    public Delivery create() {
        DeliveryId deliveryId = this.deliveryIdFactory.create();
        return new Delivery(deliveryId, possibleDeliveryLocation);
    }
}
