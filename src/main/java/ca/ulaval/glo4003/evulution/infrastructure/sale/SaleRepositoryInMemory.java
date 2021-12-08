package ca.ulaval.glo4003.evulution.infrastructure.sale;

import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import ca.ulaval.glo4003.evulution.domain.sale.SaleId;
import ca.ulaval.glo4003.evulution.domain.sale.SaleRepository;
import ca.ulaval.glo4003.evulution.domain.sale.SaleStatus;
import ca.ulaval.glo4003.evulution.infrastructure.sale.exceptions.SaleNotFoundException;

import java.util.HashMap;

public class SaleRepositoryInMemory implements SaleRepository {
    private HashMap<SaleId, Sale> sales = new HashMap<>();

    @Override
    public void registerSale(Sale sale) {
        sales.put(sale.getSaleId(), sale);
    }

    @Override
    public Sale getSale(SaleId saleId) throws SaleNotFoundException {
        Sale sale = sales.get(saleId);
        if (sale == null) {
            throw new SaleNotFoundException();
        }
        return sale;
    }

    @Override
    public AccountId getAccountIdFromSaleId(SaleId saleId) throws SaleNotFoundException {
        return getSale(saleId).getAccountId();
    }

    @Override
    public void setStatus(SaleId saleId, SaleStatus status) throws SaleNotFoundException {
        this.getSale(saleId).setStatus(status);
    }
}
