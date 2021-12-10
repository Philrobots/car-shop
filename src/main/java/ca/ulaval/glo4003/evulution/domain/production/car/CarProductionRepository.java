package ca.ulaval.glo4003.evulution.domain.production.car;

import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;
import ca.ulaval.glo4003.evulution.domain.production.exceptions.CarNotAssociatedWithManufactureException;
import ca.ulaval.glo4003.evulution.infrastructure.assemblyline.exceptions.InvalidMappingKeyException;

public interface CarProductionRepository {

    void add(CarProduction carProduction);

    void remove(ProductionId productionId) throws InvalidMappingKeyException;

    boolean replaceCarProductionWithoutManufactureIfItHasBeenMade(CarProduction carProductionAssociatedWithManufacture)
            throws CarNotAssociatedWithManufactureException;

}
