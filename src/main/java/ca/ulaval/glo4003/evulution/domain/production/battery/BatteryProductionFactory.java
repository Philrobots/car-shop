package ca.ulaval.glo4003.evulution.domain.production.battery;

import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;
import ca.ulaval.glo4003.evulution.domain.production.battery.BatteryProduction;

public class BatteryProductionFactory {

    public BatteryProduction create(ProductionId productionId, String batteryType, String email,
            int batteryProductionTime) {
        return new BatteryProduction(productionId, batteryType, email, batteryProductionTime);
    }
}
