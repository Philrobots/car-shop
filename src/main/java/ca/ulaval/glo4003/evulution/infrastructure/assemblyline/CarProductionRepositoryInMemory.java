package ca.ulaval.glo4003.evulution.infrastructure.assemblyline;

import ca.ulaval.glo4003.evulution.domain.car.CarType;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProduction;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProductionRepository;
import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProductionAssociatedWithManufacture;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProductionWithoutManufacture;
import ca.ulaval.glo4003.evulution.infrastructure.assemblyline.exceptions.InvalidMappingKeyException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Override
    public List<CarProductionWithoutManufacture> getProducedCarProductionsWithoutManufacture(CarType carType) {
        List<CarProductionWithoutManufacture> returnList = new ArrayList<>();

        for(CarProduction carProduction : vehicles.values()){
            if (!carProduction.isAssociatedWithManufacture()) returnList.add((CarProductionWithoutManufacture) carProduction);
        }
        return returnList;
    }

    @Override
    public boolean replaceCarProductionWithoutManufactureIfItHasBeenMade(CarProductionAssociatedWithManufacture carProductionAssociatedWithManufacture) {
        ProductionId removedCarProductionId = null;
        for (CarProduction carProduction : vehicles.values()){
            if (!carProduction.isAssociatedWithManufacture() && carProduction.getCarStyle().equals(carProductionAssociatedWithManufacture.getCarStyle())){
                removedCarProductionId = carProduction.getProductionId();
                break;
            }
        }

        if (removedCarProductionId != null) {
            vehicles.remove(removedCarProductionId);
            vehicles.put(carProductionAssociatedWithManufacture.getProductionId(), carProductionAssociatedWithManufacture);
            return true;
        }

        return false;
    }
}
