package ca.ulaval.glo4003.evulution.service.delivery;

import ca.ulaval.glo4003.evulution.api.delivery.dto.DeliveryCompleteDto;

import java.math.BigDecimal;

public class DeliveryCompleteAssembler {
    public DeliveryCompleteDto assemble(BigDecimal paymentsTaken, Integer paymentsLeft) {
        return new DeliveryCompleteDto(paymentsTaken.floatValue(), paymentsLeft);
    }
}
