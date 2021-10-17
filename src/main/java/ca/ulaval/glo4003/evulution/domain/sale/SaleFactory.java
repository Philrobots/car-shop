package ca.ulaval.glo4003.evulution.domain.sale;

import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryIdFactory;

public class SaleFactory {
    TransactionIdFactory transactionIdFactory;
    DeliveryIdFactory deliveryIdFactory;

    public SaleFactory(TransactionIdFactory transactionIdFactory, DeliveryIdFactory deliveryIdFactory) {
        this.transactionIdFactory = transactionIdFactory;
        this.deliveryIdFactory = deliveryIdFactory;
    }

    public Sale create(String email) {
        TransactionId transactionId = this.transactionIdFactory.create();
        DeliveryId deliveryId = this.deliveryIdFactory.create();
        return new Sale(email, transactionId, deliveryId);
    }
}
