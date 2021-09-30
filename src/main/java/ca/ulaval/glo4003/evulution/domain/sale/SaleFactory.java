package ca.ulaval.glo4003.evulution.domain.sale;

import ca.ulaval.glo4003.evulution.domain.delivery.Delivery;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryFactory;

public class SaleFactory {
    TransactionIdFactory transactionIdFactory;
    DeliveryFactory deliveryFactory;

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
