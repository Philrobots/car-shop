package ca.ulaval.glo4003.evulution.domain.assemblyline;

import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyLineType;
import ca.ulaval.glo4003.evulution.domain.assemblyline.ProductionType;
import ca.ulaval.glo4003.evulution.domain.assemblyline.car.CarAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.car.CarAssemblyLineJIT;
import ca.ulaval.glo4003.evulution.domain.assemblyline.car.CarAssemblyLineSequential;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.InvalidAssemblyLineException;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.InvalidAssemblyLineOrderException;

public class CarAssemblyLineSelector {
    private ProductionType currentProductionType = ProductionType.SEQUENTIAL;
    private CarAssemblyLineSequential carAssemblyLineSequential;
    private CarAssemblyLineJIT carAssemblyLineJIT;

    public CarAssemblyLine selectAssemblyLine(ProductionType productionType, AssemblyLineType assemblyLineType) throws InvalidAssemblyLineException, InvalidAssemblyLineOrderException {
        if (assemblyLineType.equals(AssemblyLineType.CAR)){
            if (productionType.equals(ProductionType.JIT) && currentProductionType.equals(ProductionType.JIT)) throw new InvalidAssemblyLineOrderException();
            if (productionType.equals(ProductionType.SEQUENTIAL) && currentProductionType.equals(ProductionType.SEQUENTIAL)) throw new InvalidAssemblyLineOrderException();
            if (productionType.equals(ProductionType.JIT)){
                currentProductionType = ProductionType.JIT;
                 return carAssemblyLineJIT;
            }else if (productionType.equals(ProductionType.SEQUENTIAL)){
                currentProductionType = ProductionType.SEQUENTIAL;
                 return carAssemblyLineSequential;
            }
        }
        throw new InvalidAssemblyLineException();
    }

}
