package ca.ulaval.glo4003.evulution.service.assemblyLine;

import ca.ulaval.glo4003.evulution.domain.assemblyline.ProductionLine;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.AssemblyLineIsNotShutdownException;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.AssemblyLineIsShutdownException;
import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.SwitchProductionBadParameters;

import ca.ulaval.glo4003.evulution.domain.assemblyline.switchProduction.ProductionSwitcher;
import ca.ulaval.glo4003.evulution.infrastructure.email.exceptions.EmailException;
import ca.ulaval.glo4003.evulution.service.assemblyLine.dto.SwitchProductionsDto;
import ca.ulaval.glo4003.evulution.service.assemblyLine.exceptions.ServiceErrorException;
import ca.ulaval.glo4003.evulution.service.exceptions.ServiceBadOrderOfOperationsException;

public class AssemblyLineService {

    private final ProductionLine productionLine;
    private final ProductionSwitcher productionSwitcher;

    public AssemblyLineService(ProductionLine productionLine, ProductionSwitcher productionSwitcher) {
        this.productionLine = productionLine;
        this.productionSwitcher = productionSwitcher;
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

    public void switchProductions(SwitchProductionsDto switchProductionsDto) throws SwitchProductionBadParameters {

        try {
            this.productionSwitcher.switchProduction(switchProductionsDto);
        } catch (SwitchProductionBadParameters e) {
            throw new ServiceErrorException();
        }
    }
}
