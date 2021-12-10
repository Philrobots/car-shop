package ca.ulaval.glo4003.evulution.domain.production.car;

import ca.ulaval.glo4003.evulution.domain.car.CarType;
import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;
import ca.ulaval.glo4003.evulution.domain.production.exceptions.CarNotAssociatedWithManufactureException;
import ca.ulaval.glo4003.evulution.infrastructure.assemblyline.exceptions.InvalidMappingKeyException;

import java.util.List;

public interface CarProductionRepository {

    void add(CarProduction carProduction);

    void remove(ProductionId productionId) throws InvalidMappingKeyException;

    List<CarProductionWithoutManufacture> getProducedCarProductionsWithoutManufacture(CarType carType);

    boolean replaceCarProductionWithoutManufactureIfItHasBeenMade(CarProduction carProductionAssociatedWithManufacture)
            throws CarNotAssociatedWithManufactureException;

}
