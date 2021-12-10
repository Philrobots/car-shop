package ca.ulaval.glo4003.evulution.domain.assemblyline.battery;

import ca.ulaval.glo4003.evulution.domain.production.battery.BatteryProduction;

public interface BatteryAssemblyLine {
    void addProduction(BatteryProduction batteryProduction);

    void advance();

    void shutdown();

    void reactivate();

    void startNext();
}
