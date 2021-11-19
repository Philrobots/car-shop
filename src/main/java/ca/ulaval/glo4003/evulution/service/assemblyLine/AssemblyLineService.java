package ca.ulaval.glo4003.evulution.service.assemblyLine;

import ca.ulaval.glo4003.evulution.domain.assemblyline.ProductionLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.battery.BatteryAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.CompleteCarAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.Vehicle.VehicleAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.production.BatteryProduction;
import ca.ulaval.glo4003.evulution.domain.production.ProductionAssembler;
import ca.ulaval.glo4003.evulution.domain.production.VehicleProduction;
import ca.ulaval.glo4003.evulution.domain.sale.Sale;

public class AssemblyLineService {

    private final ProductionAssembler productionAssembler;
    private final ProductionLine productionLine;

    public AssemblyLineService(ProductionAssembler productionAssembler, ProductionLine productionLine) {
        this.productionAssembler = productionAssembler;
        this.productionLine = productionLine;
    }

    public void addSaleToAssemblyLines(Sale sale) {
        VehicleProduction vehicleProduction = productionAssembler.assembleVehicleProductionFromSale(sale);
        BatteryProduction batteryProduction = productionAssembler.assembleBatteryProductionFromSale(sale);
        this.productionLine.addSaleToAssemblyLines(vehicleProduction, batteryProduction, sale);
    }

    public void advanceAssemblyLines() {
        this.productionLine.advanceAssemblyLines();
    }

    public void shutdown() {
        this.productionLine.shutdown();
    }

    public void reactivate() {
        this.productionLine.reactivate();
    }
}
