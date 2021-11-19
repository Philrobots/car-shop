package ca.ulaval.glo4003.evulution.domain.assemblyline.Vehicle;

import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyStatus;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

public interface VehicleAssemblyAdapter {

    AssemblyStatus getStatus(TransactionId transactionId);

    void newVehicleCommand(TransactionId transactionId, String command);

    void advance();
}