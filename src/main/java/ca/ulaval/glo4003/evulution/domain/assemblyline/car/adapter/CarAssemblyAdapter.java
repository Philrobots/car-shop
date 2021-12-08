package ca.ulaval.glo4003.evulution.domain.assemblyline.car.adapter;

import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;

public interface CarAssemblyAdapter {

    boolean isAssembled(ProductionId productionId);

    void newVehicleCommand(ProductionId productionId, String command);

    void advance();
}
