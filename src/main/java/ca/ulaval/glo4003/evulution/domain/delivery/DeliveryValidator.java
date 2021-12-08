package ca.ulaval.glo4003.evulution.domain.delivery;

import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.delivery.exceptions.MismatchAccountIdWithDeliveryException;
import ca.ulaval.glo4003.evulution.infrastructure.delivery.exceptions.DeliveryNotFoundException;

public class DeliveryValidator {
    private final DeliveryRepository deliveryRepository;

    public DeliveryValidator(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    public void validateDeliveryOwner(DeliveryId deliveryId, AccountId accountId)
            throws DeliveryNotFoundException, MismatchAccountIdWithDeliveryException {
        Delivery delivery = this.deliveryRepository.getDelivery(deliveryId);
        delivery.verifyAccountId(accountId);
    }
}
