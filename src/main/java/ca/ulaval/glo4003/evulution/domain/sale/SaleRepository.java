package ca.ulaval.glo4003.evulution.domain.sale;

import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;

public interface SaleRepository {

    void registerSale(Sale sale);

    Sale getSale(TransactionId transactionId);

    Sale getSaleFromDeliveryId(DeliveryId deliveryId);

    void setStatus(TransactionId transactionId, SaleStatus status);
}
