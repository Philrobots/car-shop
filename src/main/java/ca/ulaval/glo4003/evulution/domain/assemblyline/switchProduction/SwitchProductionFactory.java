package ca.ulaval.glo4003.evulution.domain.assemblyline.switchProduction;

import ca.ulaval.glo4003.evulution.domain.assemblyline.exceptions.SwitchProductionBadParameters;
import ca.ulaval.glo4003.evulution.service.assemblyLine.dto.SwitchProductionsDto;

public class SwitchProductionFactory {
    public SwitchProduction create(SwitchProductionsDto switchProductionsDto) throws SwitchProductionBadParameters {
        if (!switchProductionsDto.lineType.equals("CAR") && switchProductionsDto.productionMode.equals("JIT")) {
            throw new SwitchProductionBadParameters();
        }
        return new SwitchProduction(switchProductionsDto.lineType, switchProductionsDto.productionMode);
    }
}
