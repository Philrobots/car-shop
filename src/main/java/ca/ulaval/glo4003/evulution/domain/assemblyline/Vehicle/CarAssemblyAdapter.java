package ca.ulaval.glo4003.evulution.domain.assemblyline.Vehicle;

import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyStatus;
import ca.ulaval.glo4003.evulution.domain.manufacture.ProductionId;

public interface CarAssemblyAdapter {

    AssemblyStatus getStatus(ProductionId productionId);

    void newVehicleCommand(ProductionId productionId, String command);

    void advance();
}
