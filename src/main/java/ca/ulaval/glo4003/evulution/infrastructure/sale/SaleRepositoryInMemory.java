package ca.ulaval.glo4003.evulution.infrastructure.sale;

import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import ca.ulaval.glo4003.evulution.domain.sale.SaleRepository;
import ca.ulaval.glo4003.evulution.domain.sale.SaleStatus;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;
import ca.ulaval.glo4003.evulution.infrastructure.sale.exceptions.SaleNotFoundFromDeliveryIdException;

import java.util.HashMap;

public class SaleRepositoryInMemory implements SaleRepository {
    private HashMap<TransactionId, Sale> sales = new HashMap<>();

    @Override
    public void registerSale(Sale sale) {
        sales.put(sale.getTransactionId(), sale);
    }

    @Override
    public Sale getSale(TransactionId transactionId) {
        return sales.get(transactionId);
    }

    @Override
    public Sale getSaleFromDeliveryId(DeliveryId deliveryId) {
        for (Sale sale : sales.values()) {
            if (sale.getDeliveryId().equals(deliveryId)) {
                return sale;
            }
        }
        throw new SaleNotFoundFromDeliveryIdException();
    }

    @Override
    public void setStatus(TransactionId transactionId, SaleStatus status) {
        this.getSale(transactionId).setStatus(status);
    }
}
