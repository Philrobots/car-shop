package ca.ulaval.glo4003.evulution.domain.production.car;

import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;
import ca.ulaval.glo4003.evulution.domain.production.car.CarProduction;

public class CarProductionFactory {
    private static boolean IS_ASSOCIATED_WITH_MANUFACTURE = true;
    private static boolean IS_NOT_ASSOCIATED_WITH_MANUFACTURE = false;

    public CarProduction create(ProductionId productionId, String carStyle, String email, int carProductionTime) {
        return new CarProduction(productionId, carStyle, email, carProductionTime, IS_ASSOCIATED_WITH_MANUFACTURE);
    }
    public CarProduction createCompact(String carType, String email, int carProductionTime) {
        return new CarProduction(new ProductionId(), carType, email, carProductionTime, IS_NOT_ASSOCIATED_WITH_MANUFACTURE);
    }
    public CarProduction createSubCompact(ProductionId productionId, String carType, String email, int carProductionTime) {
        return new CarProduction(productionId, carType, email, carProductionTime, IS_ASSOCIATED_WITH_MANUFACTURE);
    }
    public CarProduction createLuxury(ProductionId productionId, String carType, String email, int carProductionTime) {
        return new CarProduction(productionId, carType, email, carProductionTime, IS_ASSOCIATED_WITH_MANUFACTURE);
    }
}
