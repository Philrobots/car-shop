package ca.ulaval.glo4003.evulution.domain.sale;

import ca.ulaval.glo4003.evulution.domain.delivery.Delivery;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryFactory;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryIdFactory;

public class SaleFactory {
    private final TransactionIdFactory transactionIdFactory;
    private final DeliveryFactory deliveryFactory;

    public SaleFactory(TransactionIdFactory transactionIdFactory, DeliveryFactory deliveryFactory) {
        this.transactionIdFactory = transactionIdFactory;
        this.deliveryFactory = deliveryFactory;
    }

    public Sale create(String email) {
        TransactionId transactionId = this.transactionIdFactory.create();
        Delivery delivery = this.deliveryFactory.create();
        return new Sale(email, transactionId, delivery);
    }
}
