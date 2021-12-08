package ca.ulaval.glo4003.evulution.domain.assemblyline.battery;

import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyStatus;
import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;

public interface BatteryAssemblyAdapter {
    AssemblyStatus getStatus(ProductionId productionId);

    void newBatteryCommand(ProductionId productionId, String command);

    void advance();
}
