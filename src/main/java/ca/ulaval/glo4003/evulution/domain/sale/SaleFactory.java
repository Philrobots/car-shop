package ca.ulaval.glo4003.evulution.domain.sale;

import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryIdFactory;

public class SaleFactory {
    private final Integer assemblyTimeInWeeks;
    private final TransactionIdFactory transactionIdFactory;
    private final DeliveryIdFactory deliveryIdFactory;

    public SaleFactory(Integer assemblyTimeInWeeks, TransactionIdFactory transactionIdFactory,
            DeliveryIdFactory deliveryIdFactory) {
        this.assemblyTimeInWeeks = assemblyTimeInWeeks;
        this.transactionIdFactory = transactionIdFactory;
        this.deliveryIdFactory = deliveryIdFactory;
    }

    public Sale create(String email) {
        TransactionId transactionId = this.transactionIdFactory.create();
        DeliveryId deliveryId = this.deliveryIdFactory.create();
        return new Sale(email, this.assemblyTimeInWeeks, transactionId, deliveryId);
    }
}
