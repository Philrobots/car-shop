package ca.ulaval.glo4003.evulution.infrastructure.assemblyline;

import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;
import ca.ulaval.glo4003.evulution.domain.production.battery.BatteryProduction;
import ca.ulaval.glo4003.evulution.domain.production.battery.BatteryProductionRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatteryProductionRepositoryInMemory implements BatteryProductionRepository {
    private final Map<ProductionId, BatteryProduction> batteries = new HashMap<>();

    @Override
    public void add(BatteryProduction batteryProduction) {
        this.batteries.put(batteryProduction.getProductionId(), batteryProduction);
    }

    @Override
    public void remove(ProductionId productionId) {
        this.batteries.remove(productionId);
    }

    public List<BatteryProduction> getAndSendToProduction() {
        if (batteries.isEmpty()) {
            return new ArrayList<>();
        }
        List<BatteryProduction> returnedList = new ArrayList<>(batteries.values());
        batteries.clear();
        return returnedList;
    }
}
