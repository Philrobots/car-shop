package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.domain.assemblyline.car.CarAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.InvalidAssemblyLineException;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.InvalidAssemblyLineOrderException;
import ca.ulaval.glo4003.evulution.domain.assemblyline.mediator.AssemblyLineMediatorSwitcher;

public class ProductionSwitcher {

    private final CarAssemblyLine carAssemblyLineJustInTime;
    private final CarAssemblyLine carAssemblyLineSequential;
    private final ProductionLine productionLine;
    private ProductionType currentProductionType = ProductionType.SEQUENTIAL;
    private AssemblyLineMediatorSwitcher assemblyLineMediatorSwitcher;

    public ProductionSwitcher(CarAssemblyLine carAssemblyLineJustInTime, CarAssemblyLine carAssemblyLineSequential,
            ProductionLine productionLine, AssemblyLineMediatorSwitcher assemblyLineMediatorSwitcher) {
        this.carAssemblyLineJustInTime = carAssemblyLineJustInTime;
        this.carAssemblyLineSequential = carAssemblyLineSequential;
        this.productionLine = productionLine;
        this.assemblyLineMediatorSwitcher = assemblyLineMediatorSwitcher;
    }

    public void switchProduction(AssemblyLineType assemblyLineType, ProductionType productionType)
            throws InvalidAssemblyLineOrderException, InvalidAssemblyLineException {
        if (assemblyLineType.equals(AssemblyLineType.CAR)) {
            if (productionType.equals(ProductionType.JIT) && currentProductionType.equals(ProductionType.JIT))
                throw new InvalidAssemblyLineOrderException();
            if (productionType.equals(ProductionType.SEQUENTIAL)
                    && currentProductionType.equals(ProductionType.SEQUENTIAL))
                throw new InvalidAssemblyLineOrderException();
            if (currentProductionType.equals(ProductionType.JIT)) {
                this.currentProductionType = ProductionType.SEQUENTIAL;
                this.productionLine.setCarAssemblyLine(this.carAssemblyLineSequential);
                this.assemblyLineMediatorSwitcher.setCarAssemblyLine(this.carAssemblyLineSequential);
            } else if (currentProductionType.equals(ProductionType.SEQUENTIAL)) {
                this.currentProductionType = ProductionType.JIT;
                this.productionLine.setCarAssemblyLine(this.carAssemblyLineJustInTime);
                this.assemblyLineMediatorSwitcher.setCarAssemblyLine(this.carAssemblyLineJustInTime);
            }
        }
        throw new InvalidAssemblyLineException();
    }
}
