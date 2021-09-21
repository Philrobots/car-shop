package ca.ulaval.glo4003.evulution.infrastructure.sale;

import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import ca.ulaval.glo4003.evulution.domain.sale.SaleRepository;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
