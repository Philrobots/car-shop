package ca.ulaval.glo4003.evulution.service.assemblyLine;

import ca.ulaval.glo4003.evulution.domain.assemblyline.VehicleAssemblyLine;

import ca.ulaval.glo4003.evulution.domain.sale.Sale;

public class VehicleAssemblyLineService {

    private VehicleAssemblyLine vehicleAssemblyLine;

    public VehicleAssemblyLineService(VehicleAssemblyLine vehicleAssemblyLine) {
        this.vehicleAssemblyLine = vehicleAssemblyLine;
    }

    public void completeVehicleCommand(Sale sale) {
        this.vehicleAssemblyLine.completeVehicleCommand(sale);
    }

}
