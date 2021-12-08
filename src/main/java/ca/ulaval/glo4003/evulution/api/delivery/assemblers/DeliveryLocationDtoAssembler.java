package ca.ulaval.glo4003.evulution.api.delivery.assemblers;

import ca.ulaval.glo4003.evulution.api.delivery.dto.DeliveryLocationRequest;
import ca.ulaval.glo4003.evulution.service.delivery.dto.DeliveryLocationDto;

public class DeliveryLocationDtoAssembler {
    public DeliveryLocationDto fromRequest(DeliveryLocationRequest deliveryLocationRequest) {
        DeliveryLocationDto deliveryLocationDto = new DeliveryLocationDto();
        deliveryLocationDto.location = deliveryLocationRequest.location;
        deliveryLocationDto.mode = deliveryLocationRequest.mode;
        return deliveryLocationDto;
    }
}
