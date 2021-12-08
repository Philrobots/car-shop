package ca.ulaval.glo4003.evulution.domain.assemblyline.switchProduction;

import ca.ulaval.glo4003.evulution.domain.assemblyline.ProductionLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.ProductionType;
import ca.ulaval.glo4003.evulution.domain.assemblyline.car.CarAssemblyLineSequential;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.SwitchProductionBadParameters;
import ca.ulaval.glo4003.evulution.service.assemblyLine.dto.SwitchProductionsDto;

public class ProductionSwitcher {

    private final CarAssemblyLineSequential carAssemblyLineSequentialJustInTime;
    private final CarAssemblyLineSequential carAssemblyLineSequential;
    private final ProductionLine productionLine;
    private SwitchProductionFactory switchProductionFactory;
    private ProductionType productionType = ProductionType.SEQUENTIAL;

    public ProductionSwitcher(CarAssemblyLineSequential carAssemblyLineSequentialJustInTime,
            CarAssemblyLineSequential carAssemblyLineSequential, ProductionLine productionLine,
            SwitchProductionFactory switchProductionFactory) {
        this.carAssemblyLineSequentialJustInTime = carAssemblyLineSequentialJustInTime;
        this.carAssemblyLineSequential = carAssemblyLineSequential;
        this.productionLine = productionLine;
        this.switchProductionFactory = switchProductionFactory;
    }

    public void switchProduction(SwitchProductionsDto switchProductionDto) throws SwitchProductionBadParameters {
        SwitchProduction switchProduction = this.switchProductionFactory.create(switchProductionDto);

        if (switchProduction.shouldGoSequential(productionType)) {
            this.productionLine.setCarAssemblyLine(carAssemblyLineSequential);
            this.productionType = ProductionType.SEQUENTIAL;
        } else {
            this.productionLine.setCarAssemblyLine(carAssemblyLineSequentialJustInTime);
            this.productionType = ProductionType.JUST_IN_TIME;
        }
    }
}
