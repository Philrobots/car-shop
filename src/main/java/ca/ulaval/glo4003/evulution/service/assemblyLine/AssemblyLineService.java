package ca.ulaval.glo4003.evulution.service.assemblyLine;

import ca.ulaval.glo4003.evulution.domain.assemblyline.BatteryAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.CompleteCarAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.VehicleAssemblyLine;

import ca.ulaval.glo4003.evulution.domain.sale.Sale;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

public class AssemblyLineService {

    private final VehicleAssemblyLine vehicleAssemblyLine;
    private final BatteryAssemblyLine batteryAssemblyLine;
    private final CompleteCarAssemblyLine completeCarAssemblyLine;

    public AssemblyLineService(VehicleAssemblyLine vehicleAssemblyLine, BatteryAssemblyLine batteryAssemblyLine,
            CompleteCarAssemblyLine completeCarAssemblyLine) {
        this.vehicleAssemblyLine = vehicleAssemblyLine;
        this.batteryAssemblyLine = batteryAssemblyLine;
        this.completeCarAssemblyLine = completeCarAssemblyLine;
    }

    public void completeVehicleCommand(Sale sale) {
        TransactionId transactionId = sale.getTransactionId();
        this.vehicleAssemblyLine.completeVehicleCommand(transactionId, sale.getCar());
        this.batteryAssemblyLine.completeBatteryCommand(transactionId, sale.getBattery());
        this.completeCarAssemblyLine.completeCarCommand(sale.getDelivery());
    }
}
