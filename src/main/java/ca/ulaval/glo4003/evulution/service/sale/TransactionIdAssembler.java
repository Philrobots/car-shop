package ca.ulaval.glo4003.evulution.service.sale;

import ca.ulaval.glo4003.evulution.api.sale.dto.SaleCreatedDto;
import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.sale.TransactionId;

public class TransactionIdAssembler {
    public SaleCreatedDto transactionIdToDto(TransactionId transactionId, DeliveryId deliveryId) {
        return new SaleCreatedDto(transactionId.getId(), deliveryId.getId());
    }

}
