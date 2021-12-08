package ca.ulaval.glo4003.evulution.domain.delivery;

import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.BadDeliveryLocationException;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.BadDeliveryModeException;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.DeliveryIncompleteException;
import ca.ulaval.glo4003.evulution.infrastructure.delivery.exceptions.DeliveryNotFoundException;

public class DeliveryDomainService {

    private DeliveryDetailsFactory deliveryDetailsFactory;
    private DeliveryRepository deliveryRepository;

    public DeliveryDomainService(DeliveryDetailsFactory deliveryDetailsFactory, DeliveryRepository deliveryRepository) {
        this.deliveryDetailsFactory = deliveryDetailsFactory;
        this.deliveryRepository = deliveryRepository;
    }

    public void setDeliveryModeLocationAndConfirmDelivery(DeliveryId deliveryId, String mode, String location)
            throws BadDeliveryModeException, BadDeliveryLocationException, DeliveryNotFoundException,
            DeliveryIncompleteException {
        DeliveryDetails deliveryDetails = deliveryDetailsFactory.create(mode, location);
        Delivery delivery = deliveryRepository.getDelivery(deliveryId);
        delivery.setDeliveryDetails(deliveryDetails);
        deliveryRepository.updateDelivery(delivery);
    }

    public void completeDelivery(DeliveryId deliveryId) throws DeliveryNotFoundException, DeliveryIncompleteException {
        Delivery delivery = deliveryRepository.getDelivery(deliveryId);
        delivery.completeDelivery();
        deliveryRepository.updateDelivery(delivery);
    }
}
