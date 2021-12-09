package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyLineType;
import ca.ulaval.glo4003.evulution.domain.assemblyline.ProductionLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.ProductionType;
import ca.ulaval.glo4003.evulution.domain.assemblyline.car.CarAssemblyLineSequential;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.InvalidAssemblyLineException;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.InvalidAssemblyLineOrderException;

public class ProductionSwitcher {

    private final CarAssemblyLineSequential carAssemblyLineSequentialJustInTime;
    private final CarAssemblyLineSequential carAssemblyLineSequential;
    private final ProductionLine productionLine;
    private ProductionType currentProductionType = ProductionType.SEQUENTIAL;

    public ProductionSwitcher(CarAssemblyLineSequential carAssemblyLineSequentialJustInTime,
            CarAssemblyLineSequential carAssemblyLineSequential, ProductionLine productionLine) {
        this.carAssemblyLineSequentialJustInTime = carAssemblyLineSequentialJustInTime;
        this.carAssemblyLineSequential = carAssemblyLineSequential;
        this.productionLine = productionLine;
    }

    public void switchProduction(AssemblyLineType assemblyLineType, ProductionType productionType) throws InvalidAssemblyLineOrderException, InvalidAssemblyLineException {
        if (assemblyLineType.equals(AssemblyLineType.CAR)){
            if (currentProductionType.equals(ProductionType.JIT) && currentProductionType.equals(ProductionType.JIT)) throw new InvalidAssemblyLineOrderException();
            if (currentProductionType.equals(ProductionType.SEQUENTIAL) && currentProductionType.equals(ProductionType.SEQUENTIAL)) throw new InvalidAssemblyLineOrderException();
            if (currentProductionType.equals(ProductionType.JIT)){
                currentProductionType = ProductionType.JIT;
                productionLine.setCarAssemblyLine(carAssemblyLineSequentialJustInTime);
            }else if (currentProductionType.equals(ProductionType.SEQUENTIAL)){
                currentProductionType = ProductionType.SEQUENTIAL;
                productionLine.setCarAssemblyLine(carAssemblyLineSequential);
            }
        }
        throw new InvalidAssemblyLineException();
    }
}
