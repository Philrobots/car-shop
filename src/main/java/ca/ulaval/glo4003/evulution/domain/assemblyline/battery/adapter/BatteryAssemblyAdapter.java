package ca.ulaval.glo4003.evulution.domain.assemblyline.battery.adapter;

import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;

public interface BatteryAssemblyAdapter {
    boolean isAssembled(ProductionId productionId);

    void newBatteryCommand(ProductionId productionId, String command);

    void advance();
}
