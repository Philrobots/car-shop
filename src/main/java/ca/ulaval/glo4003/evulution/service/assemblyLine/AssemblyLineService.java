package ca.ulaval.glo4003.evulution.service.assemblyLine;

import ca.ulaval.glo4003.evulution.domain.assemblyline.CarAssemblyLineSelector;
import ca.ulaval.glo4003.evulution.domain.assemblyline.AssemblyLineType;
import ca.ulaval.glo4003.evulution.domain.assemblyline.ProductionLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.ProductionType;
import ca.ulaval.glo4003.evulution.domain.assemblyline.car.CarAssemblyLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.AssemblyLineIsNotShutdownException;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.AssemblyLineIsShutdownException;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.InvalidAssemblyLineException;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.InvalidAssemblyLineOrderException;
import ca.ulaval.glo4003.evulution.infrastructure.email.exceptions.EmailException;
import ca.ulaval.glo4003.evulution.service.assemblyLine.dto.SwitchProductionsDto;
import ca.ulaval.glo4003.evulution.service.assemblyLine.exceptions.ServiceErrorException;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadInputParameterException;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadOrderOfOperationsException;

public class AssemblyLineService {

    private final ProductionLine productionLine;
    private CarAssemblyLineSelector carAssemblyLineSelector;

    public AssemblyLineService(ProductionLine productionLine) {
        this.productionLine = productionLine;
    }

    public void shutdown() {
        try {
            this.productionLine.shutdown();
        } catch (AssemblyLineIsShutdownException e) {
            throw new ServiceBadOrderOfOperationsException();
        } catch (EmailException e) {
            throw new ServiceErrorException();
        }
    }

    public void reactivate() {
        try {
            this.productionLine.reactivate();
        } catch (AssemblyLineIsNotShutdownException e) {
            throw new ServiceBadOrderOfOperationsException();
        } catch (EmailException e) {
            throw new ServiceErrorException();
        }
    }

    public void switchProductions(SwitchProductionsDto switchProductionsDto) {
        try{

            ProductionType productionType = ProductionType.valueOf(switchProductionsDto.productionMode);
            AssemblyLineType assemblyLineType = AssemblyLineType.valueOf(switchProductionsDto.lineType);
            CarAssemblyLine carAssemblyLine = carAssemblyLineSelector.selectAssemblyLine(productionType, assemblyLineType);
            productionLine.setCarAssemblyLine(carAssemblyLine);

        } catch (IllegalArgumentException | InvalidAssemblyLineException e){
            throw new ServiceBadInputParameterException();
        } catch(InvalidAssemblyLineOrderException e){
            throw new ServiceBadOrderOfOperationsException();
        }
    }
}
