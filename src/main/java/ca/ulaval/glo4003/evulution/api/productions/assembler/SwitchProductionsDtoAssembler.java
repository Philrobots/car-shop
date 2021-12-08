package ca.ulaval.glo4003.evulution.api.productions.assembler;

import ca.ulaval.glo4003.evulution.api.productions.dto.SwitchProductionsRequest;
import ca.ulaval.glo4003.evulution.service.assemblyLine.dto.SwitchProductionsDto;

public class SwitchProductionsDtoAssembler {
    public SwitchProductionsDto fromRequest(SwitchProductionsRequest switchProductionsRequest) {
        return new SwitchProductionsDto(switchProductionsRequest.line_type, switchProductionsRequest.production_mode);
    }
}
