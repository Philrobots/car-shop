package ca.ulaval.glo4003.evulution.infrastructure.assemblyline;

import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.BatteryRepository;
import ca.ulaval.glo4003.evulution.domain.production.BatteryProduction;
import ca.ulaval.glo4003.evulution.infrastructure.assemblyline.exceptions.InvalidMappingKeyException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatteryRepositoryInMemory implements BatteryRepository {
    private final Map<String, BatteryProduction> batteries = new HashMap<>();

    @Override
    public void add(String name, BatteryProduction batteryProduction) {
        this.batteries.put(name, batteryProduction);
    }

    @Override
    public void remove(String name) {
        BatteryProduction batteryProduction = this.batteries.remove(name);
        if (batteryProduction == null) {
            throw new InvalidMappingKeyException();
        }
    }

    public List<BatteryProduction> getAndSendToProduction() {
        if (batteries.isEmpty())
            return new ArrayList<>();
        List<BatteryProduction> returnedList = new ArrayList<>(batteries.values());
        batteries.clear();
        return returnedList;
    }
}
