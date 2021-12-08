package ca.ulaval.glo4003.evulution.api.sale.assemblers;

import ca.ulaval.glo4003.evulution.api.sale.dto.ChooseCarRequest;
import ca.ulaval.glo4003.evulution.service.manufacture.dto.ChooseCarDto;

public class ChooseCarDtoAssembler {
    public ChooseCarDto fromRequest(ChooseCarRequest chooseCarRequest) {
        ChooseCarDto chooseCarDto = new ChooseCarDto();
        chooseCarDto.name = chooseCarRequest.name;
        chooseCarDto.color = chooseCarRequest.color;
        return chooseCarDto;
    }
}
