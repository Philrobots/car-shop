package ca.ulaval.glo4003.evulution.infrastructure.sale;

import ca.ulaval.glo4003.evulution.api.exceptions.BadInputParameterException;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import ca.ulaval.glo4003.evulution.domain.sale.SaleRepository;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class SaleRepositoryInMemory implements SaleRepository {

    private HashMap<TransactionId, Sale> sales = new HashMap<>();

    @Override
    public void registerSale(Sale sale) {
        sales.put(sale.getTransactionId(), sale);
    }

    @Override
    public List<Sale> getSales() {
        return (List<Sale>) sales.values();
    }

    @Override
    public Sale getSale(TransactionId transactionId) {
        return sales.get(transactionId);
    }

    @Override
    public Sale getSaleFromDeliveryId(DeliveryId deliveryId) {
        Collection<Sale> sales = this.sales.values();
        for (Sale sale : sales) {
            if (sale.getDeliveryId().equals(deliveryId)) {
                return sale;
            }
        }
        throw new BadInputParameterException();
    }
}
