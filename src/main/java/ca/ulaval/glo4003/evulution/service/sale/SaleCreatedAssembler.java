package ca.ulaval.glo4003.evulution.service.sale;

import ca.ulaval.glo4003.evulution.domain.delivery.DeliveryId;
import ca.ulaval.glo4003.evulution.domain.sale.SaleId;
import ca.ulaval.glo4003.evulution.service.sale.dto.SaleCreatedDto;

public class SaleCreatedAssembler {
    public SaleCreatedDto assemble(SaleId saleId, DeliveryId deliveryId) {
        return new SaleCreatedDto(saleId.getId(), deliveryId.getId());
    }

}
