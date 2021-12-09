package ca.ulaval.glo4003.evulution.domain.assemblyline.battery;

import ca.ulaval.glo4003.evulution.domain.production.battery.BatteryProduction;
import ca.ulaval.glo4003.evulution.infrastructure.email.exceptions.EmailException;

public interface BatteryAssemblyLine {
    void addProduction(BatteryProduction batteryProduction);

    void advance() throws EmailException;

    void shutdown();

    void reactivate() throws EmailException;

    void startNext() throws EmailException;
}
