package ca.ulaval.glo4003.evulution.domain.assemblyline.Vehicle;

import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;
import ca.ulaval.glo4003.evulution.domain.production.CarProduction;
import ca.ulaval.glo4003.evulution.infrastructure.assemblyline.exceptions.InvalidMappingKeyException;

public interface VehicleRepository {

    void add(CarProduction carProduction);

    void remove(ProductionId productionId) throws InvalidMappingKeyException;
}
