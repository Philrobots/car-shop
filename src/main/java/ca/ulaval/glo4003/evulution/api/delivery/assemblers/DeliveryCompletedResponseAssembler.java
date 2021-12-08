package ca.ulaval.glo4003.evulution.api.delivery.assemblers;

import ca.ulaval.glo4003.evulution.api.delivery.dto.DeliveryCompletedResponse;
import ca.ulaval.glo4003.evulution.service.delivery.dto.DeliveryCompleteDto;

public class DeliveryCompletedResponseAssembler {
    public DeliveryCompletedResponse toResponse(DeliveryCompleteDto deliveryCompleteDto) {
        DeliveryCompletedResponse deliveryCompletedResponse = new DeliveryCompletedResponse();
        deliveryCompletedResponse.number_of_payment_left = deliveryCompleteDto.numberOfPaymentLeft;
        deliveryCompletedResponse.payment_taken = deliveryCompleteDto.paymentTaken;
        return deliveryCompletedResponse;
    }
}
