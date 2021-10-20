package ca.ulaval.glo4003.evulution.domain.delivery;

import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.BadDeliveryLocationException;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.BadDeliveryModeException;

import java.util.List;

public class DeliveryDetailsFactory {
    private final List<String> possibleDeliveryLocation;

    public DeliveryDetailsFactory(List<String> possibleDeliveryLocation) {
        this.possibleDeliveryLocation = possibleDeliveryLocation;
    }

    public DeliveryDetails create(String mode, String location) {
        if (!mode.equals("At campus"))
            throw new BadDeliveryModeException();
        if (!possibleDeliveryLocation.contains(location))
            throw new BadDeliveryLocationException();
        return new DeliveryDetails(mode, location);
    }
}
