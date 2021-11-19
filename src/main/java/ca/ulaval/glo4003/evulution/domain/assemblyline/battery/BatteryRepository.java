package ca.ulaval.glo4003.evulution.domain.assemblyline.battery;

import ca.ulaval.glo4003.evulution.domain.production.BatteryProduction;

import java.util.List;

public interface BatteryRepository {

    void add(String name, BatteryProduction batteryProduction);

    void remove(String name);

    List<BatteryProduction> getAndSendToProduction();

}
