package ca.ulaval.glo4003.evulution.domain.delivery;

import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.BadDeliveryLocationException;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.BadDeliveryModeException;

import java.util.List;

public class DeliveryFactory {
    List<String> possibleDeliveryLocation;

    public DeliveryFactory(List<String> possibleDeliveryLocation) {
        this.possibleDeliveryLocation = possibleDeliveryLocation;
    }

    public Delivery create(String mode, String location) {
        if (!mode.equals("At campus"))
            throw new BadDeliveryModeException();
        if (!possibleDeliveryLocation.contains(location))
            throw new BadDeliveryLocationException();

        return new Delivery(mode, location);
    }
}
