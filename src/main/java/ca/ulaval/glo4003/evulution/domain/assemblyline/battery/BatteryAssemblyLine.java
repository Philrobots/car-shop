package ca.ulaval.glo4003.evulution.domain.assemblyline.battery;

import ca.ulaval.glo4003.evulution.domain.production.battery.BatteryProduction;
import ca.ulaval.glo4003.evulution.domain.email.exceptions.EmailException;

public interface BatteryAssemblyLine {
    void addProduction(BatteryProduction batteryProduction);

    void advance();

    void shutdown();

    void reactivate();

    void startNext();
}
