package ca.ulaval.glo4003.evulution.service.assemblyLine;

import ca.ulaval.glo4003.evulution.domain.assemblyline.BatteryAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.VehicleAssemblyLine;

import ca.ulaval.glo4003.evulution.domain.sale.Sale;

public class AssemblyLineService {

    private final VehicleAssemblyLine vehicleAssemblyLine;
    private final BatteryAssemblyLine batteryAssemblyLine;

    public AssemblyLineService(VehicleAssemblyLine vehicleAssemblyLine, BatteryAssemblyLine batteryAssemblyLine) {
        this.vehicleAssemblyLine = vehicleAssemblyLine;
        this.batteryAssemblyLine = batteryAssemblyLine;
    }

    public void completeVehicleCommand(Sale sale) {
        this.vehicleAssemblyLine.completeVehicleCommand(sale);
        this.batteryAssemblyLine.completeBatteryCommand(sale);
    }
}
