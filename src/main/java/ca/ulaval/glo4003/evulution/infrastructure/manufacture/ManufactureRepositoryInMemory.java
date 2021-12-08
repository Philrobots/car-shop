package ca.ulaval.glo4003.evulution.infrastructure.manufacture;

import ca.ulaval.glo4003.evulution.domain.manufacture.Manufacture;
import ca.ulaval.glo4003.evulution.domain.manufacture.ManufactureRepository;
import ca.ulaval.glo4003.evulution.domain.sale.SaleId;

import java.util.Map;

public class ManufactureRepositoryInMemory implements ManufactureRepository {
    private final ManufactureDao manufactureDao;

    public ManufactureRepositoryInMemory(ManufactureDao manufactureDao) {
        this.manufactureDao = manufactureDao;
    }

    @Override
    public void addManufacture(SaleId saleId, Manufacture manufacture) {
        manufactureDao.add(saleId, manufacture);
    }

    @Override
    public Manufacture getBySaleId(SaleId saleId) {
        return manufactureDao.getBySaleId(saleId);
    }

    @Override
    public void updateManufacture(SaleId saleId, Manufacture manufacture) {
        manufactureDao.add(saleId, manufacture);
    }

    @Override
    public Map<SaleId, Manufacture> getManufacturesReadyForProduction() {
        return manufactureDao.getManufacturesReadyForProduction();
    }
}
