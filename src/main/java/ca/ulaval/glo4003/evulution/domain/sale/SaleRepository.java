package ca.ulaval.glo4003.evulution.domain.sale;

import ca.ulaval.glo4003.evulution.domain.account.AccountId;
import ca.ulaval.glo4003.evulution.infrastructure.sale.exceptions.SaleNotFoundException;

public interface SaleRepository {

    void registerSale(Sale sale);

    Sale getSale(SaleId saleId) throws SaleNotFoundException;

    AccountId getAccountIdFromSaleId(SaleId saleId) throws SaleNotFoundException;

    void setStatus(SaleId saleId, SaleStatus status) throws SaleNotFoundException;
}
