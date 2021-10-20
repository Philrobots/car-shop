package ca.ulaval.glo4003.evulution.domain.delivery;

import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.BadDeliveryLocationException;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.BadDeliveryModeException;

import java.util.List;

public class DeliveryFactory {
    private Integer assemblyTimeInWeeks;
    private DeliveryIdFactory deliveryIdFactory;

    public DeliveryFactory(Integer assemblyTimeInWeeks, DeliveryIdFactory deliveryIdFactory) {
        this.assemblyTimeInWeeks = assemblyTimeInWeeks;
        this.deliveryIdFactory = deliveryIdFactory;
    }

    public Delivery create() {
        return new Delivery(deliveryIdFactory.create(), assemblyTimeInWeeks);
    }
}
