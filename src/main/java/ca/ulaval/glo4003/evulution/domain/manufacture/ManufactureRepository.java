package ca.ulaval.glo4003.evulution.domain.manufacture;

import ca.ulaval.glo4003.evulution.domain.sale.SaleId;

import java.util.Map;

public interface ManufactureRepository {
    void addManufacture(SaleId saleId, Manufacture manufacture);

    Manufacture getBySaleId(SaleId saleId);

    void updateManufacture(SaleId saleId, Manufacture manufacture);

    Map<SaleId, Manufacture> getManufacturesReadyForProduction();
}
