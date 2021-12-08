package ca.ulaval.glo4003.evulution.api.sale.assemblers;

import ca.ulaval.glo4003.evulution.api.sale.dto.ChooseBatteryRequest;
import ca.ulaval.glo4003.evulution.service.manufacture.dto.ChooseBatteryDto;

public class ChooseBatteryDtoAssembler {
    public ChooseBatteryDto fromRequest(ChooseBatteryRequest chooseBatteryRequest) {
        ChooseBatteryDto chooseBatteryDto = new ChooseBatteryDto();
        chooseBatteryDto.type = chooseBatteryRequest.type;
        return chooseBatteryDto;
    }
}
