package ca.ulaval.glo4003.evulution.domain.delivery;

import ca.ulaval.glo4003.evulution.domain.sale.SaleId;
import ca.ulaval.glo4003.evulution.infrastructure.delivery.exceptions.DeliveryNotFoundException;

public interface DeliveryRepository {
    Delivery getDelivery(DeliveryId deliveryId) throws DeliveryNotFoundException;

    SaleId getSaleId(DeliveryId deliveryId) throws DeliveryNotFoundException;

    void updateDelivery(Delivery delivery);
}
