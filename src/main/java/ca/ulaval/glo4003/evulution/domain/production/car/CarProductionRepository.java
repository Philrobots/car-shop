package ca.ulaval.glo4003.evulution.domain.production.car;

import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;
import ca.ulaval.glo4003.evulution.domain.production.exceptions.CarNotAssociatedWithManufactureException;

public interface CarProductionRepository {

    void add(CarProduction carProduction);

    void remove(ProductionId productionId);

    boolean replaceCarProductionWithoutManufactureIfItHasBeenMade(CarProduction carProductionAssociatedWithManufacture)
            throws CarNotAssociatedWithManufactureException;

}
