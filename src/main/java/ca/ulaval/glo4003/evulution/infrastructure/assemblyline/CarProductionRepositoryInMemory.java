package ca.ulaval.glo4003.evulution.infrastructure.assemblyline;

import ca.ulaval.glo4003.evulution.domain.production.car.CarProductionRepository;
import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProduction;
import ca.ulaval.glo4003.evulution.infrastructure.assemblyline.exceptions.InvalidMappingKeyException;

import java.util.HashMap;
import java.util.Map;

public class CarProductionRepositoryInMemory implements CarProductionRepository {
    private final Map<ProductionId, CarProduction> vehicles = new HashMap<>();

    @Override
    public void add(CarProduction carProduction) {
        this.vehicles.put(carProduction.getProductionId(), carProduction);
    }

    @Override
    public void remove(ProductionId productionId) throws InvalidMappingKeyException {
        CarProduction carProduction = this.vehicles.remove(productionId);
        if (carProduction == null) {
            throw new InvalidMappingKeyException();
        }
    }
}
