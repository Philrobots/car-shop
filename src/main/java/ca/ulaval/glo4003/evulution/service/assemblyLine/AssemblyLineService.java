package ca.ulaval.glo4003.evulution.service.assemblyLine;

import ca.ulaval.glo4003.evulution.domain.assemblyline.BatteryAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.CompleteCarAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.VehicleAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.production.ProductionAssembler;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;

public class AssemblyLineService {

    private final VehicleAssemblyLine vehicleAssemblyLine;
    private final BatteryAssemblyLine batteryAssemblyLine;
    private final CompleteCarAssemblyLine completeCarAssemblyLine;
    private final ProductionAssembler productionAssembler;

    public AssemblyLineService(VehicleAssemblyLine vehicleAssemblyLine, BatteryAssemblyLine batteryAssemblyLine,
            CompleteCarAssemblyLine completeCarAssemblyLine, ProductionAssembler productionAssembler) {
        this.vehicleAssemblyLine = vehicleAssemblyLine;
        this.batteryAssemblyLine = batteryAssemblyLine;
        this.completeCarAssemblyLine = completeCarAssemblyLine;
        this.productionAssembler = productionAssembler;
    }

    public void addSaleToAssemblyLines(Sale sale) {
        this.vehicleAssemblyLine.addProduction(productionAssembler.assembleVehicleProductionFromSale(sale));
        this.batteryAssemblyLine.addProduction(productionAssembler.assembleBatteryProductionFromSale(sale));
        this.completeCarAssemblyLine.addCommand(sale);
    }

    public void advanceAssemblyLines() {
        this.vehicleAssemblyLine.advance();
        this.batteryAssemblyLine.advance();
        this.completeCarAssemblyLine.advance();
    }
}
