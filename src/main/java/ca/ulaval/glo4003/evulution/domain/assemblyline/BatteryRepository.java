package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.domain.production.BatteryProduction;

public interface BatteryRepository {

    void add(String name, BatteryProduction batteryProduction);

    void remove(String name);
}
