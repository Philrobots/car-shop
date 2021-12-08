package ca.ulaval.glo4003.evulution.domain.production.car;

import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProduction;

public class CarProductionFactory {
    public CarProduction create(ProductionId productionId, String carType, String email, int carProductionTime) {
        return new CarProduction(productionId, carType, email, carProductionTime);
    }
}
